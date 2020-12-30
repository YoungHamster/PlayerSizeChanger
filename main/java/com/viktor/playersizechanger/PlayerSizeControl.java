package com.viktor.playersizechanger;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

public class PlayerSizeControl {
    private static float sizemultiplier = 1f;
    private static final float sizecapmax = 100f;
    private static final float sizecapmin = 0.2f;
    private static boolean playerSizeApplied = true;

    public static void increaseMultiplier(){
        if(sizemultiplier * 1.2f >= sizecapmax) return;
        sizemultiplier = sizemultiplier * 1.2f;
        playerSizeApplied = false;
    }

    public static void decreaseMultiplier(){
        if(sizemultiplier * 0.8f <= sizecapmin) return;
        sizemultiplier = sizemultiplier * 0.8f;
        playerSizeApplied = false;
    }

    @SubscribeEvent
    public void changePlayerSizeOnTick(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.START) {
            event.player.eyeHeight = (float) (event.player.getPoseAABB(event.player.getPose()).maxY - event.player.getPoseAABB(event.player.getPose()).maxY / 10);
            event.player.stepHeight = (float) (event.player.getPoseAABB(event.player.getPose()).maxY / 3.6);
        }
        if(!playerSizeApplied){
            changePlayerSize(event.player);
            event.player.setPose(event.player.getPose());
        }
    }

    private static void changePlayerSize(PlayerEntity player){
        Map<Pose, EntitySize> map = ImmutableMap.<Pose, EntitySize>builder()
                .put(Pose.STANDING, EntitySize.flexible(0.6F*sizemultiplier, 1.8F*sizemultiplier))
                .put(Pose.SLEEPING, EntitySize.flexible(0.6F*sizemultiplier, 1.8F*sizemultiplier))
                .put(Pose.FALL_FLYING, EntitySize.flexible(0.6F*sizemultiplier, 0.6F*sizemultiplier))
                .put(Pose.SWIMMING, EntitySize.flexible(0.6F*sizemultiplier, 0.6F*sizemultiplier))
                .put(Pose.SPIN_ATTACK, EntitySize.flexible(0.6F*sizemultiplier, 0.6F*sizemultiplier))
                .put(Pose.CROUCHING, EntitySize.flexible(0.6F*sizemultiplier, 1.5F*sizemultiplier))
                .put(Pose.DYING, EntitySize.fixed(0.2F*sizemultiplier, 0.2F*sizemultiplier)).build();
        player.SIZE_BY_POSE = map;
        player.STANDING_SIZE = EntitySize.flexible(0.6F*sizemultiplier, 1.8F*sizemultiplier);
    }

    @SubscribeEvent
    public void onPlayerRenderPre(RenderPlayerEvent.Pre event){
        event.getMatrixStack().push();
        event.getMatrixStack().scale(sizemultiplier, sizemultiplier, sizemultiplier);
    }

    @SubscribeEvent
    public void onPlayerRenderPost(RenderPlayerEvent.Post event){
        event.getMatrixStack().pop();
    }

}
