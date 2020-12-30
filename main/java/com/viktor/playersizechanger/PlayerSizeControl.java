package com.viktor.playersizechanger;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

public class PlayerSizeControl {
    private float sizemultiplier = 0.5f;

    @SubscribeEvent
    public void changePlayerSizeOnTick(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.START) {
            event.player.eyeHeight = (float) (event.player.getPoseAABB(event.player.getPose()).maxY - event.player.getPoseAABB(event.player.getPose()).maxY / 10);
            event.player.stepHeight = (float) (event.player.getPoseAABB(event.player.getPose()).maxY / 3.6);
        }
    }

    @SubscribeEvent
    public void changePlayerSizeOnLogIn(PlayerEvent.PlayerLoggedInEvent event){
        Map<Pose, EntitySize> map = ImmutableMap.<Pose, EntitySize>builder()
                .put(Pose.STANDING, EntitySize.flexible(0.6F*sizemultiplier, 1.8F*sizemultiplier))
                .put(Pose.SLEEPING, EntitySize.flexible(0.6F*sizemultiplier, 1.8F*sizemultiplier))
                .put(Pose.FALL_FLYING, EntitySize.flexible(0.6F*sizemultiplier, 0.6F*sizemultiplier))
                .put(Pose.SWIMMING, EntitySize.flexible(0.6F*sizemultiplier, 0.6F*sizemultiplier))
                .put(Pose.SPIN_ATTACK, EntitySize.flexible(0.6F*sizemultiplier, 0.6F*sizemultiplier))
                .put(Pose.CROUCHING, EntitySize.flexible(0.6F*sizemultiplier, 1.5F*sizemultiplier))
                .put(Pose.DYING, EntitySize.fixed(0.2F*sizemultiplier, 0.2F*sizemultiplier)).build();
        PlayerEntity.SIZE_BY_POSE = map;
        PlayerEntity.STANDING_SIZE = EntitySize.flexible(0.6F*sizemultiplier, 1.8F*sizemultiplier);
    }

    @SubscribeEvent
    public void onRenderTick(RenderPlayerEvent.Pre event){
        GlStateManager.scalef(sizemultiplier, sizemultiplier ,sizemultiplier);
    }

    @SubscribeEvent
    public void onRenderTick(RenderPlayerEvent.Post event){
        GlStateManager.scalef(1 / sizemultiplier,1 / sizemultiplier ,1 / sizemultiplier);
    }

}
