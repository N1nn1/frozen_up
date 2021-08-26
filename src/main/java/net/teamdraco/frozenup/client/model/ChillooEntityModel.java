package net.teamdraco.frozenup.client.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;
import net.teamdraco.frozenup.entity.ChillooEntity;

@SuppressWarnings("FieldCanBeLocal, unused")
@Environment(EnvType.CLIENT)
public class ChillooEntityModel extends AnimalModel<ChillooEntity> {
    private final ModelPart root;

    private final ModelPart body;
    private final ModelPart body_feathers;
    private final ModelPart tail;
    private final ModelPart tail_feathers;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart head;
    private final ModelPart head_feathers;
    private final ModelPart left_whiskers;
    private final ModelPart right_whiskers;

    public ChillooEntityModel(ModelPart root) {
        super(true, 0.0F, 0.0F);
        this.root = root;

        this.body       = root.getChild("body");
        this.head       = root.getChild("head");

        this.left_whiskers       = head.getChild("left_whiskers");
        this.right_whiskers       = head.getChild("right_whiskers");
        this.head_feathers       = head.getChild("head_feathers");

        this.right_leg       = body.getChild("right_leg");
        this.left_leg       = body.getChild("left_leg");
        this.body_feathers       = body.getChild("body_feathers");
        this.tail       = body.getChild("tail");

        this.tail_feathers       = tail.getChild("tail_feathers");
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            "body",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-5.0F, -3.0F, 0.0F, 10.0F, 9.0F, 16.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 12.0F, -8.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData body_feathers = body.addChild(
            "body_feathers",
            ModelPartBuilder.create()
                            .uv(3, 25)
                            .mirrored(false)
                            .cuboid(-5.0F, 6.0F, 0.0F, 10.0F, 3.0F, 16.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tail = body.addChild(
            "tail",
            ModelPartBuilder.create()
                            .uv(52, 0)
                            .mirrored(false)
                            .cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 18.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 16.0F, -0.3491F, 0.0F, 0.0F)
        );

        ModelPartData tail_feathers = tail.addChild(
            "tail_feathers",
            ModelPartBuilder.create()
                            .uv(60, 42)
                            .mirrored(false)
                            .cuboid(-1.5F, 3.0F, 0.0F, 3.0F, 4.0F, 18.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData right_leg = body.addChild(
            "right_leg",
            ModelPartBuilder.create()
                            .uv(0, 25)
                            .mirrored(false)
                            .cuboid(-2.0F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(-4.5F, 1.0F, 7.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData left_leg = body.addChild(
            "left_leg",
            ModelPartBuilder.create()
                            .uv(0, 25)
                            .mirrored(true)
                            .cuboid(-3.0F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(4.5F, 1.0F, 7.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData head = root.addChild(
            "head",
            ModelPartBuilder.create()
                            .uv(36, 0)
                            .mirrored(false)
                            .cuboid(-4.0F, -3.0F, -9.0F, 8.0F, 6.0F, 9.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 15.0F, -8.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData head_feathers = head.addChild(
            "head_feathers",
            ModelPartBuilder.create()
                            .uv(0, 55)
                            .mirrored(false)
                            .cuboid(-3.0F, 0.0F, -5.5F, 6.0F, 2.0F, 7.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 3.0F, -2.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData left_whiskers = head.addChild(
            "left_whiskers",
            ModelPartBuilder.create()
                            .uv(52, 21)
                            .mirrored(true)
                            .cuboid(-3.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)),
            ModelTransform.of(-4.0F, 1.5F, -4.0F, 0.0F, -0.7854F, 0.0F)
        );

        ModelPartData right_whiskers = head.addChild(
            "right_whiskers",
            ModelPartBuilder.create()
                            .uv(52, 21)
                            .mirrored(false)
                            .cuboid(0.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)),
            ModelTransform.of(4.0F, 1.5F, -4.0F, 0.0F, 0.7854F, 0.0F)
        );

        return TexturedModelData.of(data, 112, 64);
    }

    @Override
    public void animateModel(ChillooEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        float speed = 1.0f;
        float degree = 1.0f;
        if (entity.isInSittingPose()) {
            if (entity.isBaby()) {
                this.head.pivotY = 20f;
                this.head.pivotZ = -2f;
            } else {
                this.head.pivotY = 8f;
                this.head.pivotZ = -2f;
            }

            this.body.setPivot(0.0F, 9.0F, -2.0F);
            this.setRotateAngle(body, -1.0471975511965976F, 0.0F, 0.0F);

            this.tail.setPivot(0.0F, 0.0F, 14.0F);
            this.setRotateAngle(tail, 1.7453292519943295F, 0.0F, 0.0F);

            this.left_leg.setPivot(-4.5F, 4.0F, 12.0F);
            this.setRotateAngle(left_leg, -0.47123889803846897F, 0.17453292519943295F, 0.2617993877991494F);

            this.right_leg.setPivot(4.5F, 4.0F, 12.0F);
            this.setRotateAngle(right_leg, -0.47123889803846897F, -0.17453292519943295F, -0.2617993877991494F);
        } else if (entity.isBaby()) {
            this.head.roll = MathHelper.cos(limbAngle * speed * 0.4F) * degree * 0.2F * limbDistance;
            this.head.pivotY = 22f;
            this.head.pivotZ = -4f;

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
        } else {
            this.head.roll = MathHelper.cos(limbAngle * speed * 0.4F) * degree * 0.2F * limbDistance;
            this.head.pivotY = 14f;
            this.head.pivotZ = -8f;

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
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body);
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