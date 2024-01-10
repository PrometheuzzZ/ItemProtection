package pj.itemprotection.ru.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ClientPlayerInteractionManager.class}, priority=100)
public class ItemUseHook {


    @Inject(method={"attackBlock"}, at={@At(value="HEAD")}, cancellable=true)
    private void attackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {

        ItemStack activeItem = MinecraftClient.getInstance().player.getMainHandStack();

        int durability = activeItem.getMaxDamage()-activeItem.getDamage();

        if(activeItem.getMaxDamage() > 2){
            if(durability<4){
                cir.cancel();
            }
        }


    }

    @Inject(method={"attackEntity"}, at={@At(value="HEAD")}, cancellable=true)
    private void attackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {

        ItemStack activeItem = MinecraftClient.getInstance().player.getMainHandStack();

        int durability = activeItem.getMaxDamage()-activeItem.getDamage();
        if(activeItem.getMaxDamage() > 2){
            if(durability<4){
                ci.cancel();
            }
        }

    }
}
