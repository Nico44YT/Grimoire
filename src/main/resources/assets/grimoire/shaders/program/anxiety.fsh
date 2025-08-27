#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform sampler2D overlayTex;
uniform float intensity; // Expected range: 0.0 to 1.0+
uniform vec2 InSize;

out vec4 fragColor;

const int samples = 3*3;
const float basePower = 0.028; // Base distortion power

const vec3 c_r = vec3(0.9, 0.1, 0.1);
const vec3 c_g = vec3(0.1, 0.2, 0.1);
const vec3 c_b = vec3(0.1, 0.1, 0.2);

mat2 rotate2d(float angle) {
    vec2 sc = vec2(sin(angle), cos(angle));
    return mat2(sc.y, -sc.x, sc.x, sc.y);
}

void main() {
    vec2 uv = texCoord;
    vec2 center = vec2(0.5);
    float dist = distance(uv, center);

    vec4 col = texture(DiffuseSampler, uv);

    float power = basePower * intensity;// Modulate distortion power with intensity

    for (int i = 0; i < samples; i++) {
        int index = i;
        if(i % 3 == 0) index *= -1;

        vec2 offsetUV = uv;
        offsetUV -= center;
        offsetUV *= rotate2d(power * float(index) * dist);
        offsetUV += center;

        fragColor += pow(texture(DiffuseSampler, offsetUV), vec4(2.2));
    }

    fragColor /= float(samples);
    fragColor = pow(fragColor, vec4(1.0 / 2.2));// Inverse gamma correction

    float edgeRadius = 0.0;
    float edgeRadiusEnd = 0.8;
    float edgeGradient = smoothstep(edgeRadius, edgeRadiusEnd, dist);

    vec3 rgb = vec3(dot(fragColor.rgb, c_r), dot(fragColor.rgb, c_g), dot(fragColor.rgb, c_b));

    // Intensity also modulates the red filter effect near edges
    vec3 resultColor = mix(rgb, fragColor.rgb, 1.0 - edgeGradient * intensity);

    fragColor = vec4(resultColor, 1.0);
}
