package net.villagerzock.projektarbeit.item.model;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Transform {
    public static final Transform DEFAULT = new Transform(new Vector3f(0.7f,0.7f,0.7f),new Vector3f(),new Quaternionf());
    public static final Transform EMPTY = new Transform(new Vector3f(),new Vector3f(),new Quaternionf());
    private final Vector3f position;
    private final Vector3f scale;
    private final Quaternionf rotation;

    public Transform(Vector3f position, Vector3f scale, Quaternionf rotation) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public Vector3f getPosition() {
        return position;
    }
}
