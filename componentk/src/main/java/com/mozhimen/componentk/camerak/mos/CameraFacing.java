package com.mozhimen.componentk.camerak.mos;

import com.mozhimen.componentk.camerak.cons.FacingType;

/**
 * @ClassName CameraFacing
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 22:17
 * @Version 1.0
 */
public class CameraFacing {
    public FacingType facingType;
    public int cameraId;
    public int pid;
    public int vid;

    private CameraFacing(CameraFacing.Builder builder) {
        this.facingType = builder.facingType;
        this.cameraId = builder.cameraId;
        this.pid = builder.pid;
        this.vid = builder.vid;
    }

    public static final class Builder {
        public FacingType facingType;
        public int cameraId;
        public int pid;
        public int vid;

        public Builder() {
            this.facingType = FacingType.BACK;
            this.cameraId = 0;
            this.pid = 0;
            this.vid = 0;
        }

        public CameraFacing.Builder setFacingType(FacingType facingType) {
            this.facingType = facingType;
            return this;
        }

        public CameraFacing.Builder setCameraId(int cameraId) {
            this.cameraId = cameraId;
            return this;
        }

        public CameraFacing.Builder setProductId(int pid) {
            this.pid = pid;
            return this;
        }

        public CameraFacing.Builder setVendorId(int vid) {
            this.vid = vid;
            return this;
        }

        public CameraFacing build() {
            return new CameraFacing(this);
        }
    }
}
