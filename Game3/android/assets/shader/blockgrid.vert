

attribute vec3 a_position;

uniform mat4 u_projTrans;
uniform mat4 u_worldTrans;
uniform mat4 u_scaleTrans;

varying vec3 v_position;

void main() {
    vec4 pos = u_worldTrans * u_scaleTrans * vec4(a_position, 1.0);
    v_position = pos.xyz;
    gl_Position = u_projTrans * pos;
}