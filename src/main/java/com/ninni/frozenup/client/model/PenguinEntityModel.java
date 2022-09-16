package com.ninni.frozenup.client.model;

import com.google.common.collect.ImmutableList;
import com.ninni.frozenup.entity.PenguinEntity;
import com.ninni.frozenup.entity.PenguinMood;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal, unused")
public class PenguinEntityModel<T extends PenguinEntity> extends AnimalModel<PenguinEntity> {
    private final ModelPart root;

    private final ModelPart body;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;
    private final ModelPart head;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart egg;


    public PenguinEntityModel(ModelPart root) {
        this.root = root;

        this.body         = root.getChild("body");
        this.head         = root.getChild("head");
        this.leftLeg      = root.getChild("leftLeg");
        this.rightLeg     = root.getChild("rightLeg");

        this.leftWing     = body.getChild("leftWing");
        this.rightWing    = body.getChild("rightWing");
        this.tail         = body.getChild("tail");
        this.egg         = body.getChild("egg");
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            "body",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 19.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData egg = body.addChild(
            "egg",
            ModelPartBuilder.create()
                            .uv(28, 26)
                            .mirrored(false)
                            .cuboid(-2.0F, -1.5F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 3.0F, -3.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftWing = body.addChild(
            "leftWing",
            ModelPartBuilder.create()
                            .uv(26, 10)
                            .mirrored(false)
                            .cuboid(0.0F, 0.0F, -3.0F, 1.0F, 7.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(4.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightWing = body.addChild(
            "rightWing",
            ModelPartBuilder.create()
                            .uv(26, 10)
                            .mirrored(true)
                            .cuboid(-1.0F, 0.0F, -3.0F, 1.0F, 7.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(-4.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tail = body.addChild(
            "tail",
            ModelPartBuilder.create()
                            .uv(13, 30)
                            .mirrored(false)
                            .cuboid(-2.0F, -0.5F, 0.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 2.5F, 4.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData head = root.addChild(
            "head",
            ModelPartBuilder.create()
                            .uv(0, 16)
                            .mirrored(false)
                            .cuboid(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 8.0F, new Dilation(0.0F))
                            .uv(24, 4)
                            .mirrored(false)
                            .cuboid(-2.0F, -2.0F, -6.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 15.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftLeg = root.addChild(
            "leftLeg",
            ModelPartBuilder.create()
                            .uv(0, 30)
                            .mirrored(false)
                            .cuboid(-2.0F, 0.0F, -3.5F, 4.0F, 1.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(2.5F, 23.0F, 0.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightLeg = root.addChild(
            "rightLeg",
            ModelPartBuilder.create()
                            .uv(0, 30)
                            .mirrored(true)
                            .cuboid(-2.0F, 0.0F, -3.5F, 4.0F, 1.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(-2.5F, 23.0F, 0.5F, 0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 48, 48);
    }

    @Override
    public void animateModel(PenguinEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta);

        body.pitch = 0F;
        body.pivotY = 19.0F;
        head.pivotZ = 0F;
        head.pivotY = 15.0F;
        tail.pitch = 0F;
        tail.pivotY = 2.5F;
        tail.pivotZ = 4.0F;
        leftLeg.yaw = 0F;
        rightLeg.yaw = 0F;
        leftLeg.pivotZ = 0.5F;
        rightLeg.pivotZ = 0.5F;
        leftLeg.pivotY = 23.0F;
        rightLeg.pivotY = 23.0F;
        leftLeg.pivotX = 2.5F;
        rightLeg.pivotX = -2.5F;
        leftWing.roll = 0.0F;
        rightWing.roll = 0.0F;
        if (entity.isSliding() || entity.isTouchingWater() && !entity.hasEgg()) {
            body.pitch = ((float)Math.PI / 2);
            body.pivotY += 1F;
            head.pivotZ = -4.5F;
            head.pivotY += 6F;
            tail.pitch = -((float)Math.PI / 2);
            tail.pivotY += 1.5F;
            tail.pivotZ -= 0.5F;
            leftLeg.yaw = (float)Math.PI;
            rightLeg.yaw = (float)Math.PI;
            leftLeg.pivotZ += 1.5F;
            rightLeg.pivotZ += 1.5F;
            leftLeg.pivotY -= 2.5F;
            rightLeg.pivotY -= 2.5F;
            leftLeg.pivotX -= 1F;
            rightLeg.pivotX += 1F;
            if (entity.isTouchingWater() && !entity.hasEgg()) {
                leftWing.roll += MathHelper.cos(limbAngle * 1.5f * 0.6F) * 1.0f * 0.4F * limbDistance - 0.15F;
                rightWing.roll += MathHelper.cos(limbAngle * 1.5f * 0.6F) * 1.0f * -0.4F * limbDistance + 0.15F;
            }
        }
        if (entity.getMood() == PenguinMood.AGITATED) {
            leftWing.roll = 0.5F;
            rightWing.roll = -0.5F;
        }

        if (entity.WingsFlapTicks != 0 && entity.getMood() != PenguinMood.AGITATED) {
            leftWing.roll += MathHelper.cos((float)entity.age + tickDelta * 1.5F) * 0.2F - 0.2F;
            rightWing.roll += MathHelper.cos((float)entity.age + tickDelta * 1.5F) * -0.2F + 0.2F;
        }
    }

    @Override
    public void setAngles(PenguinEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        limbDistance = MathHelper.clamp(limbDistance, -0.45F, 0.45F);
        egg.visible = entity.hasEgg();

        float speed = 1.5f;
        float degree = 1.0f;
        head.pitch = headPitch * ((float) Math.PI / 180f);
        head.yaw = headYaw * ((float) Math.PI / 180f);
        if (entity.hasEgg()) head.pitch += 0.5F;
        if (entity.isSliding() || entity.isSubmergedInWater() && !entity.hasEgg()) {
            body.roll = 0F;
            head.roll = 0F;
            tail.yaw = 0F;
            leftWing.pitch = 0F;
            rightWing.pitch = 0F;
            leftLeg.pitch = 0F;
            rightLeg.pitch = 0F;
            leftLeg.roll = 0F;
            rightLeg.roll = 0F;
            leftLeg.pivotZ += 0.5F;
            rightLeg.pivotZ += 0.5F;
        } else if (entity.getMood() != PenguinMood.AGITATED) {
                leftWing.pitch = MathHelper.sin(limbAngle * speed * 0.2F) * degree * 0.6F * limbDistance;
                rightWing.pitch = MathHelper.sin(limbAngle * speed * 0.2F) * degree * 0.6F * limbDistance;
                body.roll = MathHelper.cos(limbAngle * speed * 0.4F) * degree * 0.2F * limbDistance;
                head.roll = MathHelper.cos(limbAngle * speed * 0.4F) * degree * 0.4F * limbDistance;
                tail.yaw = MathHelper.sin(limbAngle * speed * 0.6F) * degree * 0.8F * limbDistance;
                leftLeg.pitch = MathHelper.cos(limbAngle * speed * 0.4F) * degree * 0.8F * limbDistance;
                rightLeg.pitch = MathHelper.cos(limbAngle * speed * 0.4F + (float) Math.PI) * degree * 0.8F * limbDistance;
                leftLeg.pivotZ += MathHelper.sin(limbAngle * speed * 0.4F) * degree * 2.8F * limbDistance;
                rightLeg.pivotZ += MathHelper.sin(limbAngle * speed * 0.4F + (float) Math.PI) * degree * 2.8F * limbDistance;
        }

        if (entity.isTouchingWater() && !entity.hasEgg()) {
            leftLeg.pitch = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 1.6F * 0.25F;
            rightLeg.pitch = MathHelper.cos(animationProgress * speed * 0.4F + (float) Math.PI) * degree * 1.6F * 0.25F;
            leftWing.pitch = MathHelper.sin(animationProgress * speed * 0.1F) * degree * 0.8F * 0.25F;
            rightWing.pitch = MathHelper.sin(animationProgress * speed * 0.1F) * degree * 0.8F * 0.25F;
            body.roll = MathHelper.cos(animationProgress * speed * 0.2F) * degree * 0.2F * 0.25F;
            head.roll = MathHelper.cos(animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F;
        }

        if (entity.getMood() == PenguinMood.AGITATED) {
            float speedAgitated = 2.0f;
            float degreeAgitated = 2.0f;
            leftWing.pitch = MathHelper.sin(animationProgress * speedAgitated * 0.6F + (float) Math.PI) * degreeAgitated * 0.6F * 0.25F + (float)Math.PI;
            rightWing.pitch = MathHelper.sin(animationProgress * speedAgitated * 0.6F) * degreeAgitated * 0.6F * 0.25F + (float)Math.PI;
            body.roll = MathHelper.cos(limbAngle * speedAgitated * 0.4F) * degreeAgitated * 0.2F * limbDistance;
            head.roll = MathHelper.cos(limbAngle * speedAgitated * 0.4F) * degreeAgitated * 0.4F * limbDistance;
            tail.yaw = MathHelper.sin(limbAngle * speedAgitated * 0.6F) * degreeAgitated * 0.8F * limbDistance;
            leftLeg.pitch = MathHelper.cos(limbAngle * speedAgitated * 0.4F) * degreeAgitated * 0.8F * limbDistance;
            rightLeg.pitch = MathHelper.cos(limbAngle * speedAgitated * 0.4F + (float) Math.PI) * degreeAgitated * 0.8F * limbDistance;
            leftLeg.pivotZ += MathHelper.sin(limbAngle * speedAgitated * 0.4F) * degreeAgitated * 2.8F * limbDistance;
            rightLeg.pivotZ += MathHelper.sin(limbAngle * speedAgitated * 0.4F + (float) Math.PI) * degreeAgitated * 2.8F * limbDistance;
        }
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.child) {
            matrices.push();
            matrices.scale(0.55F, 0.55F, 0.55F);
            matrices.translate(0.0, 1.335F, 0);
            this.head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
            matrices.pop();
            matrices.push();
            matrices.scale(0.45F, 0.45F, 0.45F);
            matrices.translate(0.0, 1.84, 0.0);
            ImmutableList.of(body, leftLeg, rightLeg).forEach((part) -> part.render(matrices, vertices, light, overlay, red, green, blue, alpha));
            matrices.pop();
        } else {
            ImmutableList.of(body, leftLeg, rightLeg, head).forEach((part) -> part.render(matrices, vertices, light, overlay, red, green, blue, alpha));
        }

    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(body, leftLeg, rightLeg);
    }
}
