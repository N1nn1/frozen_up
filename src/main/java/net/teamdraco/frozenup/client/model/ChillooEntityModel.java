package net.teamdraco.frozenup.client.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;
import net.teamdraco.frozenup.entity.ChillooEntity;

@SuppressWarnings("FieldCanBeLocal, unused")
@Environment(EnvType.CLIENT)
public class ChillooEntityModel extends AnimalModel<ChillooEntity> {
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

        this.body       = root.getChild("body");
        this.head       = root.getChild("head");

        this.leftWhiskers       = head.getChild("leftWhiskers");
        this.rightWhiskers       = head.getChild("rightWhiskers");

        this.rightLeg       = body.getChild("rightLeg");
        this.leftLeg       = body.getChild("leftLeg");
        this.tail       = body.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            "body",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-5.0F, -4.5F, -8.0F, 10.0F, 9.0F, 16.0F, new Dilation(0.0F))
                            .uv(0, 25)
                            .mirrored(false)
                            .cuboid(-5.0F, 4.5F, -8.0F, 10.0F, 3.0F, 16.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 13.5F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tail = body.addChild(
            "tail",
            ModelPartBuilder.create()
                            .uv(34, 26)
                            .mirrored(false)
                            .cuboid(-1.5F, 3.0F, 0.0F, 3.0F, 4.0F, 18.0F, new Dilation(0.0F))
                            .uv(0, 44)
                            .mirrored(false)
                            .cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 18.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, -1.5F, 8.0F, -0.3491F, 0.0F, 0.0F)
        );

        ModelPartData rightLeg = body.addChild(
            "rightLeg",
            ModelPartBuilder.create()
                            .uv(58, 15)
                            .mirrored(true)
                            .cuboid(-2.0F, 0.0F, -2.5F, 5.0F, 8.0F, 5.0F, new Dilation(0.0F))
                            .uv(55, 55)
                            .mirrored(true)
                            .cuboid(-2.0F, 8.0F, -2.5F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F))
                            .uv(50, 48)
                            .mirrored(true)
                            .cuboid(-2.0F, 8.0F, -4.0F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(-4.5F, 0.5F, 0.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftLeg = body.addChild(
            "leftLeg",
            ModelPartBuilder.create()
                            .uv(58, 15)
                            .mirrored(false)
                            .cuboid(-3.0F, 0.0F, -2.5F, 5.0F, 8.0F, 5.0F, new Dilation(0.0F))
                            .uv(55, 55)
                            .mirrored(false)
                            .cuboid(-3.0F, 8.0F, -2.5F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F))
                            .uv(50, 48)
                            .mirrored(false)
                            .cuboid(-3.0F, 8.0F, -4.0F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(4.5F, 0.5F, 0.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData head = root.addChild(
            "head",
            ModelPartBuilder.create()
                            .uv(36, 0)
                            .mirrored(false)
                            .cuboid(-4.0F, -3.0F, -9.0F, 8.0F, 6.0F, 9.0F, new Dilation(0.0F))
                            .uv(24, 48)
                            .mirrored(false)
                            .cuboid(-3.0F, 3.0F, -7.5F, 6.0F, 2.0F, 7.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 15.0F, -8.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftWhiskers = head.addChild(
            "leftWhiskers",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(0.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)),
            ModelTransform.of(4.0F, 1.5F, -4.0F, 0.0F, 0.7854F, 0.0F)
        );

        ModelPartData rightWhiskers = head.addChild(
            "rightWhiskers",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(true)
                            .cuboid(-3.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)),
            ModelTransform.of(-4.0F, 1.5F, -4.0F, 0.0F, -0.7854F, 0.0F)
        );

        return TexturedModelData.of(data, 80, 80);
    }

    @Override
    public void animateModel(ChillooEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta);
        this.head.pivotY = 15.0F + entity.getNeckAngle(tickDelta) * 4.0F;
        this.headPitchModifier = entity.getHeadAngle(tickDelta);
    }

    @Override
    public void setAngles(ChillooEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        limbDistance = MathHelper.clamp(limbDistance, -0.45F, 0.45F);
        float speed = 1.0f;
        float degree = 1.0f;
        head.pitch = this.headPitchModifier;
        head.yaw = headYaw * ((float) Math.PI / 180f);
        leftWhiskers.yaw = MathHelper.cos(animationProgress * speed * 0.6F) * degree * 0.2F * 0.5F + 0.8F;
        rightWhiskers.yaw = MathHelper.cos(animationProgress * speed * 0.6F + (float)Math.PI) * degree * 0.2F * 0.5F - 0.8F;
        rightLeg.pitch = MathHelper.cos(limbAngle * speed * 0.6F) * 1.4F * limbDistance;
        leftLeg.pitch = MathHelper.cos(limbAngle * speed * 0.6F + (float)Math.PI) * 1.4F * limbDistance;
        body.roll = MathHelper.cos(limbAngle * speed * 0.4F) * 0.3F * limbDistance;
        tail.yaw = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 1F * limbDistance;
        tail.pitch = MathHelper.cos(limbAngle * speed * 0.4F+ (float)Math.PI/2) * degree * 0.2F * limbDistance - 0.2F;
        body.pitch = 0.0F;
        tail.pivotY = 0.0F;
        rightLeg.pivotZ = 0.0F;
        leftLeg.pivotZ = 0.0F;
        body.pivotY = 13.5F;
        leftLeg.roll = 0.0F;
        rightLeg.roll = 0.0F;
        head.pivotY = 15.0F;
        head.pivotZ = -8.0F;
        if (entity.isTamed()) {
            tail.yaw = MathHelper.cos(animationProgress * speed * 0.2F) * degree * 1F * 0.5F;
            tail.pitch = MathHelper.cos(animationProgress * speed * 0.4F+ (float)Math.PI/2) * degree * 0.2F * 0.5F - 0.2F;
        }

        if (entity.isInSittingPose()){
            body.pitch = -20.0F;
            body.pivotY = 13.5F + 3.5F;
            tail.pivotY = 0.4F;
            tail.pitch += 2.2F;
            rightLeg.pitch = -0.5F;
            rightLeg.roll = 0.5F;
            rightLeg.pivotZ = 5.5F;
            leftLeg.pitch = -0.5F;
            leftLeg.roll = -0.5F;
            leftLeg.pivotZ = 5.5F;
            head.pivotY = 8.5F;
            head.pivotZ = -2.5F;
            if (entity.isBaby()){
                head.pivotY = 12.5F;
                head.pivotZ = -4.5F;
            }
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
}