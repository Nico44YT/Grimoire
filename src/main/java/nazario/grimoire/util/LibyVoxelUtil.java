package nazario.grimoire.util;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Iterator;

public class LibyVoxelUtil {
    public static VoxelShape rotate(VoxelShape shape, int rotation) {
        if (rotation % 90 != 0) {
            throw new IllegalArgumentException("Rotation must be a multiple of 90");
        } else if (rotation == 0) {
            return shape;
        } else {
            VoxelShape rotatedShape = VoxelShapes.empty();

            Box box;
            for(Iterator var3 = shape.getBoundingBoxes().iterator(); var3.hasNext(); rotatedShape = VoxelShapes.union(rotatedShape, rotateBox(box, rotation))) {
                box = (Box)var3.next();
            }

            return rotatedShape;
        }
    }

    private static VoxelShape rotateBox(Box box, int rotation) {
        double minX = box.minX;
        double minY = box.minY;
        double minZ = box.minZ;
        double maxX = box.maxX;
        double maxY = box.maxY;
        double maxZ = box.maxZ;
        switch (rotation) {
            case 90 -> {
                return VoxelShapes.cuboid(1.0 - maxZ, minY, minX, 1.0 - minZ, maxY, maxX);
            }
            case 180 -> {
                return VoxelShapes.cuboid(1.0 - maxX, minY, 1.0 - maxZ, 1.0 - minX, maxY, 1.0 - minZ);
            }
            case 270 -> {
                return VoxelShapes.cuboid(minZ, minY, 1.0 - maxX, maxZ, maxY, 1.0 - minX);
            }
            default -> throw new IllegalArgumentException("Rotation must be 0, 90, 180, or 270 degrees");
        }
    }

    public static VoxelShape move(VoxelShape shape, Vec3i position) {
        return shape.offset((double)position.getX(), (double)position.getY(), (double)position.getZ());
    }
}
