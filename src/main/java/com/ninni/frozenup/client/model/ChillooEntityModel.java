package com.ninni.frozenup.client.model;

import com.google.common.collect.ImmutableList;
import com.ninni.frozenup.entity.ChillooEntity;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("FieldCanBeLocal, unused")
@OnlyIn(Dist.CLIENT)
public class ChillooEntityModel extends AgeableListModel<ChillooEntity> {
    private float headPitchModifier;

    private final ModelPart root;

    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    public final ModelPart head;
    private final ModelPart leftWhiskers;
    private final ModelPart rightWhiskers;

    public ChillooEntityModel(ModelPart root) {
        super(true, 8F, 3.35F);
        this.root = root;

        this.body = root.getChild("body");
        this.head = root.getChild("head");

        this.leftWhiskers = head.getChild("leftWhiskers");
        this.rightWhiskers = head.getChild("rightWhiskers");

        this.rightLeg  = body.getChild("rightLeg");
        this.leftLeg  = body.getChild("leftLeg");
        this.tail = body.getChild("tail");
    }

    public static LayerDefinition getLayerDefinition() {

        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition body = root.addOrReplaceChild(
            "body",
            CubeListBuilder.create()
                            .texOffs(0, 0)
                            .mirror(false)
                            .addBox(-5.0F, -4.5F, -8.0F, 10.0F, 9.0F, 16.0F, new CubeDeformation(0.0F))
                            .texOffs(0, 25)
                            .mirror(false)
                            .addBox(-5.0F, 4.5F, -8.0F, 10.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.0F, 13.5F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition tail = body.addOrReplaceChild(
            "tail",
            CubeListBuilder.create()
                            .texOffs(34, 26)
                            .mirror(false)
                            .addBox(-1.5F, 3.0F, 0.0F, 3.0F, 4.0F, 18.0F, new CubeDeformation(0.0F))
                            .texOffs(0, 44)
                            .mirror(false)
                            .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.0F, -1.5F, 8.0F, -0.3491F, 0.0F, 0.0F)
        );

        PartDefinition rightLeg = body.addOrReplaceChild(
            "rightLeg",
            CubeListBuilder.create()
                            .texOffs(58, 15)
                            .mirror(true)
                            .addBox(-2.0F, 0.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                            .texOffs(55, 55)
                            .mirror(true)
                            .addBox(-2.0F, 8.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                            .texOffs(50, 48)
                            .mirror(true)
                            .addBox(-2.0F, 8.0F, -4.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(-4.5F, 0.5F, 0.5F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition leftLeg = body.addOrReplaceChild(
            "leftLeg",
            CubeListBuilder.create()
                            .texOffs(58, 15)
                            .mirror(false)
                            .addBox(-3.0F, 0.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                            .texOffs(55, 55)
                            .mirror(false)
                            .addBox(-3.0F, 8.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                            .texOffs(50, 48)
                            .mirror(false)
                            .addBox(-3.0F, 8.0F, -4.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(4.5F, 0.5F, 0.5F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition head = root.addOrReplaceChild(
            "head",
            CubeListBuilder.create()
                            .texOffs(36, 0)
                            .mirror(false)
                            .addBox(-4.0F, -3.0F, -9.0F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                            .texOffs(24, 48)
                            .mirror(false)
                            .addBox(-3.0F, 3.0F, -7.5F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.0F, 15.0F, -8.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition leftWhiskers = head.addOrReplaceChild(
            "leftWhiskers",
            CubeListBuilder.create()
                            .texOffs(0, 0)
                            .mirror(false)
                            .addBox(0.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(4.0F, 1.5F, -4.0F, 0.0F, 0.7854F, 0.0F)
        );

        PartDefinition rightWhiskers = head.addOrReplaceChild(
            "rightWhiskers",
            CubeListBuilder.create()
                            .texOffs(0, 0)
                            .mirror(true)
                            .addBox(-3.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(-4.0F, 1.5F, -4.0F, 0.0F, -0.7854F, 0.0F)
        );

        return LayerDefinition.create(data, 80, 80);
    }

    @Override
    public void prepareMobModel(ChillooEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        super.prepareMobModel(entity, limbAngle, limbDistance, tickDelta);
        this.head.y = 15.0F + entity.getNeckAngle(tickDelta) * 4.0F;
        this.headPitchModifier = entity.getHeadAngle(tickDelta);
    }

    @Override
    public void setupAnim(ChillooEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        limbDistance = Mth.clamp(limbDistance, -0.45F, 0.45F);
        float speed = 1.0f;
        float degree = 1.0f;
        head.xRot = this.headPitchModifier;
        head.yRot = headYaw * ((float) Math.PI / 180f);
        leftWhiskers.yRot = Mth.cos(animationProgress * speed * 0.6F) * degree * 0.2F * 0.5F + 0.8F;
        rightWhiskers.yRot = Mth.cos(animationProgress * speed * 0.6F + (float)Math.PI) * degree * 0.2F * 0.5F - 0.8F;
        rightLeg.xRot = Mth.cos(limbAngle * speed * 0.6F) * 1.4F * limbDistance;
        leftLeg.xRot = Mth.cos(limbAngle * speed * 0.6F + (float)Math.PI) * 1.4F * limbDistance;
        body.zRot = Mth.cos(limbAngle * speed * 0.4F) * 0.3F * limbDistance;
        tail.yRot = Mth.cos(limbAngle * speed * 0.2F) * degree * 1F * limbDistance;
        tail.xRot = Mth.cos(limbAngle * speed * 0.4F+ (float)Math.PI/2) * degree * 0.2F * limbDistance - 0.2F;
        body.xRot = 0.0F;
        tail.y = 0.0F;
        rightLeg.z = 0.0F;
        leftLeg.z = 0.0F;
        body.y = 13.5F;
        leftLeg.zRot = 0.0F;
        rightLeg.zRot = 0.0F;
        head.y = 15.0F;
        head.z = -8.0F;
        if (entity.isTame()) {
            tail.yRot = Mth.cos(animationProgress * speed * 0.2F) * degree * 1F * 0.5F;
            tail.xRot = Mth.cos(animationProgress * speed * 0.4F+ (float)Math.PI/2) * degree * 0.2F * 0.5F - 0.2F;
        }

        if (entity.isInSittingPose()){
            body.xRot = -20.0F;
            body.y = 13.5F + 3.5F;
            tail.y = 0.4F;
            tail.xRot += 2.2F;
            rightLeg.xRot = -0.5F;
            rightLeg.zRot = 0.5F;
            rightLeg.z = 5.5F;
            leftLeg.xRot = -0.5F;
            leftLeg.zRot = -0.5F;
            leftLeg.z = 5.5F;
            head.y = 8.5F;
            head.z = -2.5F;
            if (entity.isBaby()){
                head.y = 12.5F;
                head.z = -4.5F;
            }
        }
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body);
    }
}