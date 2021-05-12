
#ifdef GL_ES
precision mediump float;
#endif

uniform vec4 u_axisColor;
uniform vec4 u_gridColor;
uniform vec4 u_backgroundColor;

uniform float u_lineWidth;
uniform float u_gridSize;
varying vec3 v_position;

void main() {

    float halfWidth = u_lineWidth * 0.5f;

    vec2 modValue = mod(v_position.xy + vec2(halfWidth), vec2(u_gridSize));
    vec2 divValue = (v_position.xy + vec2(halfWidth)) / vec2(u_gridSize);
    vec2 gridValue = step(modValue, vec2(u_lineWidth));
    vec2 axisValue = step(divValue, vec2(1.0f)) * step(vec2(0.0f), divValue) * gridValue;

    float gridWeight = step(1.0f, gridValue.x + gridValue.y - axisValue.x - axisValue.y);
    float axisWeight = step(1.0f, axisValue.x + axisValue.y);

    gl_FragColor = u_gridColor * gridWeight * (1 - axisWeight)
                + u_axisColor * axisWeight
                + u_backgroundColor * step(gridWeight + axisWeight, 0.0f);
}