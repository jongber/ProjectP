package com.projecta.game.desktop.common.data;

public class Quad3D {

    // x, y, z
    public static final int POSITION_COMPONENT = 3;

    // uv
    public static final int UV_COMPONENT = 2;

    public static final int MAX_VERTICES = 4;
    public static final int MAX_INDICES = 6;

    public float[] vert;
    public short[] indices;

    public boolean useUV;

    public static Quad3D createQuad(boolean useUV) {
        return createQuad(useUV, 1.0f);
    }

    public static Quad3D createQuad(boolean useUV, float scale) {

        if (scale <= 0.0f || scale >= 100000000.0f) {
            scale = 1.0f;
        }

        Quad3D quad = new Quad3D();
        quad.useUV = useUV;

        createVertices(quad, scale);
        createIndices(quad);

        return quad;
    }

    private static void createVertices(Quad3D quad, float scale) {
        int length = POSITION_COMPONENT * MAX_VERTICES;
        if (quad.useUV) {
            length += UV_COMPONENT * MAX_VERTICES;
        }

        quad.vert = new float[length];

        int idx = 0;

        // clock wise
        // position
        quad.vert[idx++] =  -1.0f * scale;
        quad.vert[idx++] =  -1.0f * scale;
        quad.vert[idx++] =  0.0f;

        if (quad.useUV) {
            quad.vert[idx++] =  0.0f;
            quad.vert[idx++] =  0.0f;
        }

        // position
        quad.vert[idx++] =  -1.0f * scale;
        quad.vert[idx++] =  1.0f * scale;
        quad.vert[idx++] =  0.0f;

        if (quad.useUV) {
            quad.vert[idx++] =  0.0f;
            quad.vert[idx++] =  1.0f;
        }

        // position
        quad.vert[idx++] =  1.0f * scale;
        quad.vert[idx++] =  1.0f * scale;
        quad.vert[idx++] =  0.0f;

        if (quad.useUV) {
            quad.vert[idx++] =  1.0f;
            quad.vert[idx++] =  1.0f;
        }

        // position
        quad.vert[idx++] =  1.0f * scale;
        quad.vert[idx++] =  -1.0f * scale;
        quad.vert[idx++] =  0.0f;

        if (quad.useUV) {
            quad.vert[idx++] =  1.0f;
            quad.vert[idx++] =  0.0f;
        }
    }

    private static void createIndices(Quad3D quad) {
        int idx = 0;
        quad.indices = new short[MAX_INDICES];
        quad.indices[idx++] = 0;
        quad.indices[idx++] = 1;
        quad.indices[idx++] = 2;
        quad.indices[idx++] = 2;
        quad.indices[idx++] = 3;
        quad.indices[idx++] = 0;
    }
}
