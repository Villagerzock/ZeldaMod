package net.villagerzock.projektarbeit.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

public class BlockOverlayRenderer implements WorldRenderEvents.AfterTranslucent {
    @Override
    public void afterTranslucent(WorldRenderContext context) {

        MinecraftClient client = MinecraftClient.getInstance();
        MatrixStack matrices = context.matrixStack();
        Camera camera = context.camera();
        Vec3d camPos = camera.getPos();

        BlockPos playerPos = client.player.getBlockPos();
        int radius = 5;

        // Push MatrixStack to apply camera offset
        matrices.push();
        //matrices.translate(-camPos.x, -camPos.y, -camPos.z);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        for (BlockPos pos : BlockPos.iterateOutwards(playerPos, radius, radius, radius)) {
            if (pos.isWithinDistance(playerPos, radius + 0.5)) {
                VoxelShape shape = client.world.getBlockState(pos).getOutlineShape(client.world, pos);
                if (!shape.isEmpty()) {
                    Box box = shape.getBoundingBox().offset(pos).expand(0.002); // Slight expansion to prevent z-fighting

                    float r = 1.0f;
                    float g = 0.0f;
                    float b = 0.0f;
                    float a = 0.3f; // 30% transparent

                    drawBox(buffer, box, r, g, b, a); // NO camPos offset anymore
                }
            }
        }

        tessellator.draw();

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();

        matrices.pop(); // Restore matrix
    }

    private void drawBox(BufferBuilder buffer, Box box, float r, float g, float b, float a) {
        double minX = box.minX;
        double minY = box.minY;
        double minZ = box.minZ;
        double maxX = box.maxX;
        double maxY = box.maxY;
        double maxZ = box.maxZ;

        // Render each face of the box

        // Top
        buffer.vertex(minX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(maxX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(minX, maxY, maxZ).color(r, g, b, a).next();

        // Bottom
        buffer.vertex(minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(maxX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(minX, minY, minZ).color(r, g, b, a).next();

        // North
        buffer.vertex(minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(maxX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(maxX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(minX, maxY, minZ).color(r, g, b, a).next();

        // South
        buffer.vertex(minX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(minX, minY, maxZ).color(r, g, b, a).next();

        // West
        buffer.vertex(minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(minX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(minX, maxY, maxZ).color(r, g, b, a).next();

        // East
        buffer.vertex(maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(maxX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(maxX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(maxX, minY, maxZ).color(r, g, b, a).next();
    }
}