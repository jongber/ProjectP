
#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_position;

uniform vec3 u_camera;

void main() {
    //gl_FragColor = vec4(v_position, 1.0, 1.0);
    gl_FragColor = vec4(u_camera, 1.0);
}