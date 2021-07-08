package net.teamdraco.frozenup.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;
import net.teamdraco.frozenup.entity.ChillooEntity;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ChillooEntityModel extends AnimalModel<ChillooEntity> {
    public ModelPart body;
    public ModelPart tail;
    public ModelPart head;
    public ModelPart leftLeg;
    public ModelPart rightLeg;
    public ModelPart bodyFeathers;
    public ModelPart tailFeathers;
    public ModelPart headFeathers;

    private final List<ModelPart> headParts;
    private final List<ModelPart> bodyParts;

    public ChillooEntityModel() {
        super(true, 8f, 3f);

        this.textureWidth = 112;
        this.textureHeight = 64;
        this.rightLeg = new ModelPart(this, 0, 25);
        this.rightLeg.setPivot(4.5F, 1.0F, 7.5F);
        this.rightLeg.addCuboid(-3.0F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.headFeathers = new ModelPart(this, 0, 55);
        this.headFeathers.setPivot(0.0F, 6.0F, 0.0F);
        this.headFeathers.addCuboid(-3.0F, 0.0F, -5.5F, 6.0F, 2.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 36, 0);
        this.head.setPivot(0.0F, 12.0F, -10.0F);
        this.head.addCuboid(-4.0F, 0.0F, -7.0F, 8.0F, 6.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.tailFeathers = new ModelPart(this, 60, 42);
        this.tailFeathers.setPivot(0.0F, 0.0F, 0.0F);
        this.tailFeathers.addCuboid(-1.5F, 3.0F, 0.0F, 3.0F, 4.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.bodyFeathers = new ModelPart(this, 4, 25);
        this.bodyFeathers.setPivot(0.0F, 0.0F, 0.0F);
        this.bodyFeathers.addCuboid(-5.0F, 6.0F, 0.0F, 10.0F, 3.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPivot(0.0F, 12.0F, -8.0F);
        this.body.addCuboid(-5.0F, -3.0F, 0.0F, 10.0F, 9.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelPart(this, 52, 0);
        this.tail.setPivot(0.0F, 0.0F, 14.0F);
        this.tail.addCuboid(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, -0.5235987755982988F, 0.0F, 0.0F);
        this.leftLeg = new ModelPart(this, 0, 25);
        this.leftLeg.mirror = true;
        this.leftLeg.setPivot(-4.5F, 1.0F, 7.5F);
        this.leftLeg.addCuboid(-2.0F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.rightLeg);
        this.head.addChild(this.headFeathers);
        this.tail.addChild(this.tailFeathers);
        this.body.addChild(this.bodyFeathers);
        this.body.addChild(this.tail);
        this.body.addChild(this.leftLeg);

        this.headParts = Collections.singletonList(head);
        this.bodyParts = Collections.singletonList(body);
    }

    @Override
    public void animateModel(ChillooEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        float speed = 1.0f;
        float degree = 1.0f;
        if (entity.isInSittingPose()) {
            if (entity.isBaby()) {
                this.head.pivotY = 9f;
                this.head.pivotZ = -5f;
            } else {
                this.head.pivotY = 5.5f;
                this.head.pivotZ = -4.5f;
            }

            this.body.setPivot(0.0F, 9.0F, -2.0F);
            this.setRotateAngle(body, -1.0471975511965976F, 0.0F, 0.0F);

            this.tail.setPivot(0.0F, 0.0F, 14.0F);
            this.setRotateAngle(tail, 1.7453292519943295F, 0.0F, 0.0F);

            this.leftLeg.setPivot(-4.5F, 4.0F, 12.0F);
            this.setRotateAngle(leftLeg, -0.47123889803846897F, 0.17453292519943295F, 0.2617993877991494F);

            this.rightLeg.setPivot(4.5F, 4.0F, 12.0F);
            this.setRotateAngle(rightLeg, -0.47123889803846897F, -0.17453292519943295F, -0.2617993877991494F);
        } else {
            this.head.roll = MathHelper.cos(limbAngle * speed * 0.4F) * degree * 0.2F * limbDistance;
            this.head.pivotY = 12f;
            this.head.pivotZ = -10f;

            this.tail.yaw = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.6F * limbDistance;
            this.tail.pitch = MathHelper.cos(limbAngle * speed * 0.4F) * degree * 0.6F * limbDistance - 0.4F;
            this.tail.pivotZ = 14f;

            this.rightLeg.pitch = MathHelper.cos(1.0F + limbAngle * speed * 0.4F) * degree * 0.8F * limbDistance;
            this.rightLeg.yaw = 0f;
            this.rightLeg.roll = 0f;
            this.rightLeg.pivotX = 4.5f;
            this.rightLeg.pivotY = 1.0f;
            this.rightLeg.pivotZ = 7.5f;

            this.leftLeg.pitch = MathHelper.cos(1.0F + limbAngle * speed * 0.4F) * degree * -0.8F * limbDistance;
            this.leftLeg.yaw = 0f;
            this.leftLeg.roll = 0f;
            this.leftLeg.pivotX = -4.5f;
            this.leftLeg.pivotY = 1.0f;
            this.leftLeg.pivotZ = 7.5f;

            this.body.setPivot(0f, 12f, -8f);
            this.body.pitch = 0f;
            this.body.roll = MathHelper.cos(limbAngle * speed * 0.4F) * degree * 0.1F * limbDistance;
        }

        int timer = entity.digTimer;
        if (!entity.isInSittingPose() && timer > 0) {
            head.pivotY = getHeadRotationPointY(timer, tickDelta);
            head.pitch = getHeadRotationAngleX(timer, tickDelta);
        }
    }

    @Override
    public void setAngles(ChillooEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        if (entity.digTimer <= 0) {
            head.pitch = headPitch * ((float) Math.PI / 180f);
            head.yaw = headYaw * ((float) Math.PI / 180f);
        }
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return this.headParts;
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return this.bodyParts;
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }

    private static float getHeadRotationPointY(int timer, float partialTicks) {
        if (timer >= 3 && timer <= 36) return 14f;
        return timer < 4 ? ((float) timer - partialTicks) + 12f : -((float) (timer - 40) - partialTicks) * 0.5f + 12f;
    }

    private static float getHeadRotationAngleX(int timer, float partialTicks) {
        if (timer >= 3 && timer <= 36) {
            float f = ((float) (timer - 4) - partialTicks) / 32.0F;
            return ((float) Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return timer > 0 ? ((float) Math.PI / 5F) : 0;
        }
    }
}
