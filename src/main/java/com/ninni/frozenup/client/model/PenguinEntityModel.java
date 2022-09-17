package com.ninni.frozenup.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ninni.frozenup.entity.PenguinEntity;
import com.ninni.frozenup.entity.PenguinMood;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@SuppressWarnings("FieldCanBeLocal, unused")
public class PenguinEntityModel<T extends PenguinEntity> extends AgeableListModel<PenguinEntity> {
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

    public static LayerDefinition getLayerDefinition() {

        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild(
            "body",
            CubeListBuilder.create()
                            .texOffs(0, 0)
                            .mirror(false)
                            .addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 19.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition egg = body.addOrReplaceChild(
            "egg",
            CubeListBuilder.create()
                            .texOffs(28, 26)
                            .mirror(false)
                            .addBox(-2.0F, -1.5F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 3.0F, -3.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition leftWing = body.addOrReplaceChild(
            "leftWing",
            CubeListBuilder.create()
                            .texOffs(26, 10)
                            .mirror(false)
                            .addBox(0.0F, 0.0F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition rightWing = body.addOrReplaceChild(
            "rightWing",
            CubeListBuilder.create()
                            .texOffs(26, 10)
                            .mirror(true)
                            .addBox(-1.0F, 0.0F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition tail = body.addOrReplaceChild(
            "tail",
            CubeListBuilder.create()
                            .texOffs(13, 30)
                            .mirror(false)
                            .addBox(-2.0F, -0.5F, 0.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 2.5F, 4.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition head = root.addOrReplaceChild(
            "head",
            CubeListBuilder.create()
                            .texOffs(0, 16)
                            .mirror(false)
                            .addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                            .texOffs(24, 4)
                            .mirror(false)
                            .addBox(-2.0F, -2.0F, -6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition leftLeg = root.addOrReplaceChild(
            "leftLeg",
            CubeListBuilder.create()
                            .texOffs(0, 30)
                            .mirror(false)
                            .addBox(-2.0F, 0.0F, -3.5F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.5F, 23.0F, 0.5F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition rightLeg = root.addOrReplaceChild(
            "rightLeg",
            CubeListBuilder.create()
                            .texOffs(0, 30)
                            .mirror(true)
                            .addBox(-2.0F, 0.0F, -3.5F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, 23.0F, 0.5F, 0.0F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(data, 48, 48);
    }

    @Override
    public void prepareMobModel(PenguinEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        super.prepareMobModel(entity, limbAngle, limbDistance, tickDelta);

        body.xRot = 0F;
        body.y = 19.0F;
        head.z = 0F;
        head.y = 15.0F;
        tail.xRot = 0F;
        tail.y = 2.5F;
        tail.z = 4.0F;
        leftLeg.yRot = 0F;
        rightLeg.yRot = 0F;
        leftLeg.z = 0.5F;
        rightLeg.z = 0.5F;
        leftLeg.y = 23.0F;
        rightLeg.y = 23.0F;
        leftLeg.x = 2.5F;
        rightLeg.x = -2.5F;
        leftWing.zRot = 0.0F;
        rightWing.zRot = 0.0F;
        if (entity.isSliding() || entity.isInWater() && !entity.hasEgg()) {
            body.xRot = ((float)Math.PI / 2);
            body.y += 1F;
            head.z = -4.5F;
            head.y += 6F;
            tail.xRot = -((float)Math.PI / 2);
            tail.y += 1.5F;
            tail.z -= 0.5F;
            leftLeg.yRot = (float)Math.PI;
            rightLeg.yRot = (float)Math.PI;
            leftLeg.z += 1.5F;
            rightLeg.z += 1.5F;
            leftLeg.y -= 2.5F;
            rightLeg.y -= 2.5F;
            leftLeg.x -= 1F;
            rightLeg.x += 1F;
            if (entity.isInWater() && !entity.hasEgg()) {
                leftWing.zRot += Mth.cos(limbAngle * 1.5f * 0.6F) * 1.0f * 0.4F * limbDistance - 0.15F;
                rightWing.zRot += Mth.cos(limbAngle * 1.5f * 0.6F) * 1.0f * -0.4F * limbDistance + 0.15F;
            }
        }
        if (entity.getMood() == PenguinMood.AGITATED) {
            leftWing.zRot = 0.5F;
            rightWing.zRot = -0.5F;
        }

        if (entity.WingsFlapTicks != 0 && entity.getMood() != PenguinMood.AGITATED) {
            leftWing.zRot += Mth.cos((float)entity.tickCount + tickDelta * 1.5F) * 0.2F - 0.2F;
            rightWing.zRot += Mth.cos((float)entity.tickCount + tickDelta * 1.5F) * -0.2F + 0.2F;
        }
    }

    @Override
    public void setupAnim(PenguinEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        limbDistance = Mth.clamp(limbDistance, -0.45F, 0.45F);
        egg.visible = entity.hasEgg();

        float speed = 1.5f;
        float degree = 1.0f;
        head.xRot = headPitch * ((float) Math.PI / 180f);
        head.yRot = headYaw * ((float) Math.PI / 180f);
        if (entity.hasEgg()) head.xRot += 0.5F;
        if (entity.isSliding() || entity.isUnderWater() && !entity.hasEgg()) {
            body.zRot = 0F;
            head.zRot = 0F;
            tail.yRot = 0F;
            leftWing.xRot = 0F;
            rightWing.xRot = 0F;
            leftLeg.xRot = 0F;
            rightLeg.xRot = 0F;
            leftLeg.zRot = 0F;
            rightLeg.zRot = 0F;
            leftLeg.z += 0.5F;
            rightLeg.z += 0.5F;
        } else if (entity.getMood() != PenguinMood.AGITATED) {
                leftWing.xRot = Mth.sin(limbAngle * speed * 0.2F) * degree * 0.6F * limbDistance;
                rightWing.xRot = Mth.sin(limbAngle * speed * 0.2F) * degree * 0.6F * limbDistance;
                body.zRot = Mth.cos(limbAngle * speed * 0.4F) * degree * 0.2F * limbDistance;
                head.zRot = Mth.cos(limbAngle * speed * 0.4F) * degree * 0.4F * limbDistance;
                tail.yRot = Mth.sin(limbAngle * speed * 0.6F) * degree * 0.8F * limbDistance;
                leftLeg.xRot = Mth.cos(limbAngle * speed * 0.4F) * degree * 0.8F * limbDistance;
                rightLeg.xRot = Mth.cos(limbAngle * speed * 0.4F + (float) Math.PI) * degree * 0.8F * limbDistance;
                leftLeg.z += Mth.sin(limbAngle * speed * 0.4F) * degree * 2.8F * limbDistance;
                rightLeg.z += Mth.sin(limbAngle * speed * 0.4F + (float) Math.PI) * degree * 2.8F * limbDistance;
        }

        if (entity.isInWater() && !entity.hasEgg()) {
            leftLeg.xRot = Mth.cos(animationProgress * speed * 0.4F) * degree * 1.6F * 0.25F;
            rightLeg.xRot = Mth.cos(animationProgress * speed * 0.4F + (float) Math.PI) * degree * 1.6F * 0.25F;
            leftWing.xRot = Mth.sin(animationProgress * speed * 0.1F) * degree * 0.8F * 0.25F;
            rightWing.xRot = Mth.sin(animationProgress * speed * 0.1F) * degree * 0.8F * 0.25F;
            body.zRot = Mth.cos(animationProgress * speed * 0.2F) * degree * 0.2F * 0.25F;
            head.zRot = Mth.cos(animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F;
        }

        if (entity.getMood() == PenguinMood.AGITATED) {
            float speedAgitated = 2.0f;
            float degreeAgitated = 2.0f;
            leftWing.xRot = Mth.sin(animationProgress * speedAgitated * 0.6F + (float) Math.PI) * degreeAgitated * 0.6F * 0.25F + (float)Math.PI;
            rightWing.xRot = Mth.sin(animationProgress * speedAgitated * 0.6F) * degreeAgitated * 0.6F * 0.25F + (float)Math.PI;
            body.zRot = Mth.cos(limbAngle * speedAgitated * 0.4F) * degreeAgitated * 0.2F * limbDistance;
            head.zRot = Mth.cos(limbAngle * speedAgitated * 0.4F) * degreeAgitated * 0.4F * limbDistance;
            tail.yRot = Mth.sin(limbAngle * speedAgitated * 0.6F) * degreeAgitated * 0.8F * limbDistance;
            leftLeg.xRot = Mth.cos(limbAngle * speedAgitated * 0.4F) * degreeAgitated * 0.8F * limbDistance;
            rightLeg.xRot = Mth.cos(limbAngle * speedAgitated * 0.4F + (float) Math.PI) * degreeAgitated * 0.8F * limbDistance;
            leftLeg.z += Mth.sin(limbAngle * speedAgitated * 0.4F) * degreeAgitated * 2.8F * limbDistance;
            rightLeg.z += Mth.sin(limbAngle * speedAgitated * 0.4F + (float) Math.PI) * degreeAgitated * 2.8F * limbDistance;
        }
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.young) {
            matrices.pushPose();
            matrices.scale(0.55F, 0.55F, 0.55F);
            matrices.translate(0.0, 1.335F, 0);
            this.head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
            matrices.popPose();
            matrices.pushPose();
            matrices.scale(0.45F, 0.45F, 0.45F);
            matrices.translate(0.0, 1.84, 0.0);
            ImmutableList.of(body, leftLeg, rightLeg).forEach((part) -> part.render(matrices, vertices, light, overlay, red, green, blue, alpha));
            matrices.popPose();
        } else {
            ImmutableList.of(body, leftLeg, rightLeg, head).forEach((part) -> part.render(matrices, vertices, light, overlay, red, green, blue, alpha));
        }

    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(body, leftLeg, rightLeg);
    }

}
