

attribute vec3 a_position;

uniform mat4 u_projTrans;
uniform mat4 u_worldTrans;
uniform mat4 u_scaleTrans;

varying vec3 v_position;

void main() {
    vec4 pos = u_worldTrans * u_scaleTrans * vec4(a_position, 1.0);
    gl_Position = u_projTrans * pos;

    v_position = (pos - u_worldTrans * vec4(0.0f, 0.0f, 0.0f, 1.0f)).xyz;
}