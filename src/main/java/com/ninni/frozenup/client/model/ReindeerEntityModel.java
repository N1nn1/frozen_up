package com.ninni.frozenup.client.model;

import com.google.common.collect.ImmutableList;
import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;

@SuppressWarnings("FieldCanBeLocal, unused")
public class ReindeerEntityModel<T extends ReindeerEntity> extends AnimalModel<ReindeerEntity> {
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

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData neck = root.addChild(
            NECK,
            ModelPartBuilder.create()
                            .uv(0, 28)
                            .cuboid(-2.5F, -7.0F, -5.0F, 5.0F, 10.0F, 6.0F),
            ModelTransform.of(0.0F, 6.0F, -6.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData head = neck.addChild(
            HEAD,
            ModelPartBuilder.create()
                            .uv(0, 46)
                            .cuboid(-3.5F, -6.0F, -4.0F, 7.0F, 6.0F, 7.0F)
                            .uv(49, 21)
                            .cuboid(-3.5F, -6.0F, -4.0F, 7.0F, 6.0F, 7.0F, new Dilation(0.25F))
                            .uv(0, 0)
                            .cuboid(-2.5F, -4.0F, -8.0F, 5.0F, 4.0F, 4.0F)
                            .uv(0, 60)
                            .cuboid(-2.5F, -4.0F, -8.0F, 5.0F, 4.0F, 4.0F, new Dilation(0.25F)),
            ModelTransform.of(0.0F, -7.0F, -2.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftAntler = head.addChild(
            LEFT_HORN,
            ModelPartBuilder.create()
                            .uv(38, -5)
                            .cuboid(0.0F, -9.0F, -6.0F, 0.0F, 9.0F, 14.0F),
            ModelTransform.of(2.0F, -6.0F, -1.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightAntler = head.addChild(
            RIGHT_HORN,
            ModelPartBuilder.create()
                            .uv(38, -5)
                            .mirrored(true)
                            .cuboid(0.0F, -9.0F, -6.0F, 0.0F, 9.0F, 14.0F),
            ModelTransform.of(-2.0F, -6.0F, -1.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightRein = head.addChild(
            RIGHT_SADDLE_LINE,
            ModelPartBuilder.create()
                            .uv(0, 72)
                            .mirrored(true)
                            .cuboid(0.0F, -0.5F, 0.0F, 0.0F, 2.0F, 6.0F),
            ModelTransform.of(-3.5F, -1.5F, 3.0F, 0.3927F, 0.0F, 0.0F)
        );

        ModelPartData leftRein = head.addChild(
            LEFT_SADDLE_LINE,
            ModelPartBuilder.create()
                            .uv(0, 72)
                            .cuboid(0.0F, -0.5F, 0.0F, 0.0F, 2.0F, 6.0F),
            ModelTransform.of(3.5F, -1.5F, 3.0F, 0.3927F, 0.0F, 0.0F)
        );

        ModelPartData rightEar = head.addChild(
            RIGHT_EAR,
            ModelPartBuilder.create()
                            .uv(38, 0)
                            .mirrored(true)
                            .cuboid(-3.0F, -0.5F, -0.5F, 3.0F, 2.0F, 1.0F),
            ModelTransform.of(-3.5F, -4.5F, 1.45F, 0.0F, 0.7854F, 0.0F)
        );

        ModelPartData leftEar = head.addChild(
            LEFT_EAR,
            ModelPartBuilder.create()
                            .uv(38, 0)
                            .cuboid(0.0F, -0.5F, -0.5F, 3.0F, 2.0F, 1.0F),
            ModelTransform.of(3.5F, -4.5F, 1.45F, 0.0F, -0.7854F, 0.0F)
        );

        ModelPartData body = root.addChild(
            BODY,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(-5.0F, -3.0F, -10.0F, 10.0F, 10.0F, 18.0F)
                            .uv(24, 52)
                            .cuboid(-5.0F, -3.0F, -10.0F, 10.0F, 10.0F, 18.0F, new Dilation(0.5F)),
            ModelTransform.of(0.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightLeg = root.addChild(
            RIGHT_LEG,
            ModelPartBuilder.create()
                            .uv(26, 28)
                            .mirrored(true)
                            .cuboid(-2.0F, 0.0F, -2.5F, 4.0F, 12.0F, 5.0F),
            ModelTransform.of(-3.0F, 12.0F, 8.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftLeg = root.addChild(
            LEFT_LEG,
            ModelPartBuilder.create()
                            .uv(26, 28)
                            .cuboid(-2.0F, 0.0F, -2.5F, 4.0F, 12.0F, 5.0F),
            ModelTransform.of(3.0F, 12.0F, 8.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightArm = root.addChild(
            RIGHT_ARM,
            ModelPartBuilder.create()
                            .uv(26, 28)
                            .mirrored(true)
                            .cuboid(-2.0F, 0.0F, -2.5F, 4.0F, 12.0F, 5.0F),
            ModelTransform.of(-3.0F, 12.0F, -5.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftArm = root.addChild(
            LEFT_ARM,
            ModelPartBuilder.create()
                            .uv(26, 28)
                            .cuboid(-2.0F, 0.0F, -2.5F, 4.0F, 12.0F, 5.0F),
            ModelTransform.of(3.0F, 12.0F, -5.5F, 0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 80, 80);
    }

    @Override
    public void animateModel(ReindeerEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta);
        float angryAnim = entity.getAngryAnimationProgress(tickDelta);

        this.neck.pivotY = 7.0F;
        this.neck.pivotZ = -6.0F;
        this.neck.pitch = 0.0F;
        this.body.pitch = 0.0F;
        this.body.pivotZ = 2.0F;
        this.leftArm.pivotY = 12.0F;
        this.rightArm.pivotY = 12.0F;
        this.leftArm.pivotZ = -4.5F;
        this.rightArm.pivotZ = -4.5F;
        if (!entity.canCloudJump()) this.neck.pitch = angryAnim + (1.0F - angryAnim) * this.neck.pitch;
        else this.neck.pitch = angryAnim * 0.5F + (1.0F - angryAnim) * this.neck.pitch;
        this.body.pitch = angryAnim * -0.8F + (1.0F - angryAnim) * this.body.pitch;
        this.body.pivotZ += angryAnim * 4F + (1.0F - angryAnim) * this.body.pitch;
        this.neck.pivotY = angryAnim * -1.0F + (1.0F - angryAnim) * this.neck.pivotY;
        this.neck.pivotZ = angryAnim * -3F + (1.0F - angryAnim) * this.neck.pivotZ;
        this.leftArm.pivotY = angryAnim * 4F + (1.0F - angryAnim) * this.leftArm.pivotY;
        this.rightArm.pivotY = angryAnim * 4F + (1.0F - angryAnim) * this.rightArm.pivotY;
        this.leftArm.pivotZ = angryAnim * -4F + (1.0F - angryAnim) * this.leftArm.pivotZ;
        this.rightArm.pivotZ = angryAnim * -4F + (1.0F - angryAnim) * this.rightArm.pivotZ;
        if (entity.tailWagTicks != 0 && !entity.hasPassengers()) {
            this.neck.roll = MathHelper.cos((float)entity.age + tickDelta * 0.8F) * 0.2F;
        } else {
            this.neck.roll = 0.0F;
        }
    }

    @Override
    public void setAngles(ReindeerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        limbDistance = MathHelper.clamp(limbDistance, -0.65F, 0.65F);
        float speed = 1.0f;
        float degree = 1.0f;

        leftRein.visible = false;
        rightRein.visible = false;
        head.pitch = headPitch * ((float) Math.PI / 180f);
        head.yaw = headYaw * ((float) Math.PI / 180f);
        rightLeg.pitch = MathHelper.cos(limbAngle * speed * 0.6F) * 1.4F * limbDistance;
        leftLeg.pitch = MathHelper.cos(limbAngle * speed * 0.6F + (float)Math.PI) * 1.4F * limbDistance;
        rightArm.pitch = MathHelper.cos(limbAngle * speed * 0.6F + (float)Math.PI) * 1.4F * limbDistance;
        leftArm.pitch = MathHelper.cos(limbAngle * speed * 0.6F) * 1.4F * limbDistance;

        if (entity.canCloudJump()) {
            rightLeg.pitch = MathHelper.cos(limbAngle * speed * 0.3F) * 0.35F * limbDistance + 0.75F;
            leftLeg.pitch = MathHelper.sin(limbAngle * speed * 0.3F) * 0.35F * limbDistance + 0.75F;
            rightArm.pitch = MathHelper.sin(limbAngle * speed * 0.3F) * 0.35F * limbDistance + 0.5F;
            leftArm.pitch = MathHelper.cos(limbAngle * speed * 0.3F) * 0.35F * limbDistance + 0.5F;
            if (entity.hasPassengers()) neck.pitch += MathHelper.cos(limbAngle * speed * 0.2F) * 0.1 * limbDistance + 0.5F;
        }

        if (entity.hasPassengers()) {
            leftRein.visible = true;
            rightRein.visible = true;
            neck.pitch += MathHelper.cos(limbAngle * speed * 0.2F) * 0.1 * limbDistance;
        }
    }


    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.neck);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.leftArm, this.leftLeg, this.rightArm, this.rightLeg);
    }
}
