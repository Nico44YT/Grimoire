package nazario.grimoire.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.item.*;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class PlayerRemainsEntity extends LivingEntity implements VehicleInventory {

    private static final int MAX_SIZE = 27*2; // Maximum inventory size
    private DefaultedList<ItemStack> inventory;

    public PlayerRemainsEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.setInvulnerable(true);
        this.inventory = DefaultedList.ofSize(MAX_SIZE, ItemStack.EMPTY);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 100f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 0f);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(source.getAttacker() instanceof PlayerEntity player) {
            if(player.isSneaking()) {
                if(world.isClient) return true;

                this.dropInventory();
                this.discard();
                return true;
            }
        }
        return false;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof BowItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ShieldItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof TridentItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof FishingRodItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof CrossbowItem
        ) return ActionResult.PASS;

        if (player.world.isClient) {
            return ActionResult.PASS;
        }

        this.open(this::emitGameEvent, player);
        return ActionResult.SUCCESS; // Prevents further interaction processing
    }

    @Override
    protected void dropInventory() {
        for(int i = 0;i<inventory.size();i++) {
            ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, world);

            itemEntity.setStack(inventory.get(i));
            itemEntity.setPosition(this.getPos());

            world.spawnEntity(itemEntity);
        }
    }

    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public void resetInventory() {
        this.inventory = DefaultedList.ofSize(MAX_SIZE, ItemStack.EMPTY);
    }

    @Override
    public int size() {
        return MAX_SIZE;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return inventory.set(slot, new ItemStack(inventory.get(slot).getItem(), inventory.get(slot).getCount()-amount));
    }

    @Override
    public ItemStack removeStack(int slot) {
        return inventory.set(slot, ItemStack.EMPTY);
    }


    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return GenericContainerScreenHandler.createGeneric9x6(syncId, inv, this);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        Iterable<ItemStack> stacks = new ArrayList<ItemStack>();
        stacks.forEach(stack -> new ItemStack(Items.AIR));
        return stacks;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return new ItemStack(Items.AIR);
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    //region// No clue //
    @Override
    public @Nullable Identifier getLootTableId() {
        return null;
    }

    @Override
    public void setLootTableId(@Nullable Identifier lootTableId) {

    }

    @Override
    public long getLootTableSeed() {
        return 0;
    }

    @Override
    public void setLootTableSeed(long lootTableSeed) {

    }

    @Override
    public void markDirty() {

    }
    //endregion
}
