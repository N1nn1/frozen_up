package com.ninni.frozenup.client.model;

import com.google.common.collect.ImmutableList;
import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

import static net.minecraft.client.model.geom.PartNames.BODY;
import static net.minecraft.client.model.geom.PartNames.HEAD;
import static net.minecraft.client.model.geom.PartNames.LEFT_ARM;
import static net.minecraft.client.model.geom.PartNames.LEFT_EAR;
import static net.minecraft.client.model.geom.PartNames.LEFT_HORN;
import static net.minecraft.client.model.geom.PartNames.LEFT_LEG;
import static net.minecraft.client.model.geom.PartNames.NECK;
import static net.minecraft.client.model.geom.PartNames.RIGHT_ARM;
import static net.minecraft.client.model.geom.PartNames.RIGHT_EAR;
import static net.minecraft.client.model.geom.PartNames.RIGHT_HORN;
import static net.minecraft.client.model.geom.PartNames.RIGHT_LEG;

@SuppressWarnings("FieldCanBeLocal, unused")
public class ReindeerEntityModel<T extends ReindeerEntity> extends AgeableListModel<ReindeerEntity> {
    private static final String LEFT_SADDLE_LINE = "left_saddle_line";
    private static final String RIGHT_SADDLE_LINE = "right_saddle_line";
    private final ModelPart root;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart leftAntler;
    private final ModelPart rightAntler;
    private final ModelPart rightRein;
    private final ModelPart leftRein;
    private final ModelPart rightEar;
    private final ModelPart leftEar;
    private final ModelPart body;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightArm;
    private final ModelPart leftArm;


    public ReindeerEntityModel(ModelPart root) {
        this.root = root;

        this.body         = root.getChild(BODY);
        this.rightLeg     = root.getChild(RIGHT_LEG);
        this.leftLeg      = root.getChild(LEFT_LEG);
        this.leftArm      = root.getChild(LEFT_ARM);
        this.rightArm     = root.getChild(RIGHT_ARM);
        this.neck         = root.getChild(NECK);

        this.head         = neck.getChild(HEAD);

        this.rightAntler  = head.getChild(RIGHT_HORN);
        this.leftAntler   = head.getChild(LEFT_HORN);
        this.leftRein     = head.getChild(LEFT_SADDLE_LINE);
        this.rightRein    = head.getChild(RIGHT_SADDLE_LINE);
        this.leftEar      = head.getChild(LEFT_EAR);
        this.rightEar     = head.getChild(RIGHT_EAR);

    }

    public static LayerDefinition getLayerDefinition() {
        MeshDefinition data = new MeshDefinition();
        PartDefinition root = data.getRoot();

        PartDefinition neck = root.addOrReplaceChild(
                NECK,
                CubeListBuilder.create()
                        .texOffs(0, 28)
                        .addBox(-2.5F, -7.0F, -5.0F, 5.0F, 10.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 6.0F, -6.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition head = neck.addOrReplaceChild(
                HEAD,
                CubeListBuilder.create()
                        .texOffs(0, 46)
                        .addBox(-3.5F, -6.0F, -4.0F, 7.0F, 6.0F, 7.0F)
                        .texOffs(49, 21)
                        .addBox(-3.5F, -6.0F, -4.0F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.25F))
                        .texOffs(0, 0)
                        .addBox(-2.5F, -4.0F, -8.0F, 5.0F, 4.0F, 4.0F)
                        .texOffs(0, 60)
                        .addBox(-2.5F, -4.0F, -8.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offsetAndRotation(0.0F, -7.0F, -2.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition leftAntler = head.addOrReplaceChild(
                LEFT_HORN,
                CubeListBuilder.create()
                        .texOffs(38, -5)
                        .addBox(0.0F, -9.0F, -6.0F, 0.0F, 9.0F, 14.0F),
                PartPose.offsetAndRotation(2.0F, -6.0F, -1.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition rightAntler = head.addOrReplaceChild(
                RIGHT_HORN,
                CubeListBuilder.create()
                        .texOffs(38, -5)
                        .mirror(true)
                        .addBox(0.0F, -9.0F, -6.0F, 0.0F, 9.0F, 14.0F),
                PartPose.offsetAndRotation(-2.0F, -6.0F, -1.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition rightRein = head.addOrReplaceChild(
                RIGHT_SADDLE_LINE,
                CubeListBuilder.create()
                        .texOffs(0, 72)
                        .mirror(true)
                        .addBox(0.0F, -0.5F, 0.0F, 0.0F, 2.0F, 6.0F),
                PartPose.offsetAndRotation(-3.5F, -1.5F, 3.0F, 0.3927F, 0.0F, 0.0F)
        );

        PartDefinition leftRein = head.addOrReplaceChild(
                LEFT_SADDLE_LINE,
                CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(0.0F, -0.5F, 0.0F, 0.0F, 2.0F, 6.0F),
                PartPose.offsetAndRotation(3.5F, -1.5F, 3.0F, 0.3927F, 0.0F, 0.0F)
        );

        PartDefinition rightEar = head.addOrReplaceChild(
                RIGHT_EAR,
                CubeListBuilder.create()
                        .texOffs(38, 0)
                        .mirror(true)
                        .addBox(-3.0F, -0.5F, -0.5F, 3.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(-3.5F, -4.5F, 1.45F, 0.0F, 0.7854F, 0.0F)
        );

        PartDefinition leftEar = head.addOrReplaceChild(
                LEFT_EAR,
                CubeListBuilder.create()
                        .texOffs(38, 0)
                        .addBox(0.0F, -0.5F, -0.5F, 3.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(3.5F, -4.5F, 1.45F, 0.0F, -0.7854F, 0.0F)
        );

        PartDefinition body = root.addOrReplaceChild(
                BODY,
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-5.0F, -3.0F, -10.0F, 10.0F, 10.0F, 18.0F)
                        .texOffs(24, 52)
                        .addBox(-5.0F, -3.0F, -10.0F, 10.0F, 10.0F, 18.0F, new CubeDeformation(0.5F)),
                PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition rightLeg = root.addOrReplaceChild(
                RIGHT_LEG,
                CubeListBuilder.create()
                        .texOffs(26, 28)
                        .mirror(true)
                        .addBox(-2.0F, 0.0F, -2.5F, 4.0F, 12.0F, 5.0F),
                PartPose.offsetAndRotation(-3.0F, 12.0F, 8.5F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition leftLeg = root.addOrReplaceChild(
                LEFT_LEG,
                CubeListBuilder.create()
                        .texOffs(26, 28)
                        .addBox(-2.0F, 0.0F, -2.5F, 4.0F, 12.0F, 5.0F),
                PartPose.offsetAndRotation(3.0F, 12.0F, 8.5F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition rightArm = root.addOrReplaceChild(
                RIGHT_ARM,
                CubeListBuilder.create()
                        .texOffs(26, 28)
                        .mirror(true)
                        .addBox(-2.0F, 0.0F, -2.5F, 4.0F, 12.0F, 5.0F),
                PartPose.offsetAndRotation(-3.0F, 12.0F, -5.5F, 0.0F, 0.0F, 0.0F)
        );

        PartDefinition leftArm = root.addOrReplaceChild(
                LEFT_ARM,
                CubeListBuilder.create()
                        .texOffs(26, 28)
                        .addBox(-2.0F, 0.0F, -2.5F, 4.0F, 12.0F, 5.0F),
                PartPose.offsetAndRotation(3.0F, 12.0F, -5.5F, 0.0F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(data, 80, 80);
    }

    @Override
    public void prepareMobModel(ReindeerEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        super.prepareMobModel(entity, limbAngle, limbDistance, tickDelta);
        float angryAnim = entity.getStandAnim(tickDelta);

        this.neck.y = 7.0F;
        this.neck.z = -6.0F;
        this.neck.xRot = 0.0F;
        this.body.xRot = 0.0F;
        this.body.z = 2.0F;
        this.leftArm.y = 12.0F;
        this.rightArm.y = 12.0F;
        this.leftArm.z = -4.5F;
        this.rightArm.z = -4.5F;
        if (!entity.canCloudJump()) this.neck.xRot = angryAnim + (1.0F - angryAnim) * this.neck.xRot;
        else this.neck.xRot = angryAnim * 0.5F + (1.0F - angryAnim) * this.neck.xRot;
        this.body.xRot = angryAnim * -0.8F + (1.0F - angryAnim) * this.body.xRot;
        this.body.z += angryAnim * 4F + (1.0F - angryAnim) * this.body.xRot;
        this.neck.y = angryAnim * -1.0F + (1.0F - angryAnim) * this.neck.y;
        this.neck.z = angryAnim * -3F + (1.0F - angryAnim) * this.neck.z;
        this.leftArm.y = angryAnim * 4F + (1.0F - angryAnim) * this.leftArm.y;
        this.rightArm.y = angryAnim * 4F + (1.0F - angryAnim) * this.rightArm.y;
        this.leftArm.z = angryAnim * -4F + (1.0F - angryAnim) * this.leftArm.z;
        this.rightArm.z = angryAnim * -4F + (1.0F - angryAnim) * this.rightArm.z;
        if (entity.tailCounter != 0 && !entity.isVehicle()) {
            this.neck.zRot = Mth.cos((float)entity.tickCount + tickDelta * 0.8F) * 0.2F;
        } else {
            this.neck.zRot = 0.0F;
        }
    }

    @Override
    public void setupAnim(ReindeerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        limbDistance = Mth.clamp(limbDistance, -0.65F, 0.65F);
        float speed = 1.0f;
        float degree = 1.0f;

        leftRein.visible = false;
        rightRein.visible = false;
        head.xRot = headPitch * ((float) Math.PI / 180f);
        head.yRot = headYaw * ((float) Math.PI / 180f);
        rightLeg.xRot = Mth.cos(limbAngle * speed * 0.6F) * 1.4F * limbDistance;
        leftLeg.xRot = Mth.cos(limbAngle * speed * 0.6F + (float)Math.PI) * 1.4F * limbDistance;
        rightArm.xRot = Mth.cos(limbAngle * speed * 0.6F + (float)Math.PI) * 1.4F * limbDistance;
        leftArm.xRot = Mth.cos(limbAngle * speed * 0.6F) * 1.4F * limbDistance;

        if (entity.canCloudJump()) {
            rightLeg.xRot = Mth.cos(limbAngle * speed * 0.3F) * 0.35F * limbDistance + 0.75F;
            leftLeg.xRot = Mth.sin(limbAngle * speed * 0.3F) * 0.35F * limbDistance + 0.75F;
            rightArm.xRot = Mth.sin(limbAngle * speed * 0.3F) * 0.35F * limbDistance + 0.5F;
            leftArm.xRot = Mth.cos(limbAngle * speed * 0.3F) * 0.35F * limbDistance + 0.5F;
            if (entity.isVehicle()) neck.xRot += Mth.cos(limbAngle * speed * 0.2F) * 0.1 * limbDistance + 0.5F;
        }

        if (entity.isVehicle()) {
            leftRein.visible = true;
            rightRein.visible = true;
            neck.xRot += Mth.cos(limbAngle * speed * 0.2F) * 0.1 * limbDistance;
        }
    }


    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.neck);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.leftArm, this.leftLeg, this.rightArm, this.rightLeg);
    }
}
