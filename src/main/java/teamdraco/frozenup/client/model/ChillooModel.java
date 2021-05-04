package teamdraco.frozenup.client.model;

import teamdraco.frozenup.entity.ChillooEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;

import java.util.Collections;
import java.util.List;

public class ChillooModel extends AnimalModel<ChillooEntity> {
    public ModelPart body;
    public ModelPart tail;
    public ModelPart head;
    public ModelPart left_leg;
    public ModelPart right_leg;
    public ModelPart body_feathers;
    public ModelPart tail_feathers;
    public ModelPart head_feathers;

    private final List<ModelPart> headParts;
    private final List<ModelPart> bodyParts;

    public ChillooModel() {
        super(true, 8f, 3f);

        this.textureWidth = 112;
        this.textureHeight = 64;
        this.right_leg = new ModelPart(this, 0, 25);
        this.right_leg.setPivot(4.5F, 1.0F, 7.5F);
        this.right_leg.addCuboid(-3.0F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.head_feathers = new ModelPart(this, 0, 55);
        this.head_feathers.setPivot(0.0F, 6.0F, 0.0F);
        this.head_feathers.addCuboid(-3.0F, 0.0F, -5.5F, 6.0F, 2.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 36, 0);
        this.head.setPivot(0.0F, 12.0F, -10.0F);
        this.head.addCuboid(-4.0F, 0.0F, -7.0F, 8.0F, 6.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.tail_feathers = new ModelPart(this, 60, 42);
        this.tail_feathers.setPivot(0.0F, 0.0F, 0.0F);
        this.tail_feathers.addCuboid(-1.5F, 3.0F, 0.0F, 3.0F, 4.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.body_feathers = new ModelPart(this, 4, 25);
        this.body_feathers.setPivot(0.0F, 0.0F, 0.0F);
        this.body_feathers.addCuboid(-5.0F, 6.0F, 0.0F, 10.0F, 3.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPivot(0.0F, 12.0F, -8.0F);
        this.body.addCuboid(-5.0F, -3.0F, 0.0F, 10.0F, 9.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelPart(this, 52, 0);
        this.tail.setPivot(0.0F, 0.0F, 14.0F);
        this.tail.addCuboid(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, -0.5235987755982988F, 0.0F, 0.0F);
        this.left_leg = new ModelPart(this, 0, 25);
        this.left_leg.mirror = true;
        this.left_leg.setPivot(-4.5F, 1.0F, 7.5F);
        this.left_leg.addCuboid(-2.0F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.right_leg);
        this.head.addChild(this.head_feathers);
        this.tail.addChild(this.tail_feathers);
        this.body.addChild(this.body_feathers);
        this.body.addChild(this.tail);
        this.body.addChild(this.left_leg);

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

            this.left_leg.setPivot(-4.5F, 4.0F, 12.0F);
            this.setRotateAngle(left_leg, -0.47123889803846897F, 0.17453292519943295F, 0.2617993877991494F);

            this.right_leg.setPivot(4.5F, 4.0F, 12.0F);
            this.setRotateAngle(right_leg, -0.47123889803846897F, -0.17453292519943295F, -0.2617993877991494F);
        } else {
            this.head.roll = MathHelper.cos(limbAngle * speed * 0.4F) * degree * 0.2F * limbDistance;
            this.head.pivotY = 12f;
            this.head.pivotZ = -10f;

            this.tail.yaw = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.6F * limbDistance;
            this.tail.pitch = MathHelper.cos(limbAngle * speed * 0.4F) * degree * 0.6F * limbDistance - 0.4F;
            this.tail.pivotZ = 14f;

            this.right_leg.pitch = MathHelper.cos(1.0F + limbAngle * speed * 0.4F) * degree * 0.8F * limbDistance;
            this.right_leg.yaw = 0f;
            this.right_leg.roll = 0f;
            this.right_leg.pivotX = 4.5f;
            this.right_leg.pivotY = 1.0f;
            this.right_leg.pivotZ = 7.5f;

            this.left_leg.pitch = MathHelper.cos(1.0F + limbAngle * speed * 0.4F) * degree * -0.8F * limbDistance;
            this.left_leg.yaw = 0f;
            this.left_leg.roll = 0f;
            this.left_leg.pivotX = -4.5f;
            this.left_leg.pivotY = 1.0f;
            this.left_leg.pivotZ = 7.5f;

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

    private static float getHeadRotationAngleX(int timer, float partialTicks)
    {
        if (timer >= 3 && timer <= 36) {
            float f = ((float)(timer - 4) - partialTicks) / 32.0F;
            return ((float)Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return timer > 0 ? ((float)Math.PI / 5F) : 0;
        }
    }
}

