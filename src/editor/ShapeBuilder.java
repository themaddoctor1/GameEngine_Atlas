package editor;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import graphics.*;
import math.*;
import math.geometry.*;
import math.geometry.shapes.*;

public class ShapeBuilder {
    
    public static final String MODEL_FILETYPE = "mdl";
    private static MultiShape3D loadedModel = null;

    private int getNextInt(FileInputStream in) throws Exception {
        int val = 0;
        for(int i = 0; i < 4; i++) {
            val <<= 0b1000;
            val |= (byte) in.read();
        }
        return val;
    }
    
    private long getNextLong(FileInputStream in) throws Exception {
        long val = 0;
        for(int i = 0; i < 8; i++) {
            val <<= 0b1000;
            val |= (byte) in.read();
        }
        return val;
    }

    private double getNextDouble(FileInputStream in) throws Exception {
        byte[] buff = new byte[8];
        for(int i = 0; i < 8; i++)
            buff[i] = (byte) in.read();
        return ByteBuffer.wrap(buff).getDouble();
    }
    
    private Quaternion getNextQuaternion(FileInputStream in) throws Exception {
        return new Quaternion(
                getNextDouble(in),
                getNextDouble(in),
                getNextDouble(in),
                getNextDouble(in));
    }

    private Vector getNextVector(FileInputStream in) throws Exception {
        return new Vector(getNextDouble(in), getNextDouble(in), getNextDouble(in));
    }

    private byte[] intToBuff(int val) {
        byte[] buff = new byte[4];
        for(int i = 0; i < 4; i++)
            buff[i] = (byte) ((val >> ((3 - i) * 0b1000)) & 0xFF);
        return buff;
    }

    private byte[] longToBuff(int val) {
        byte[] buff = new byte[8];
        for(int i = 0; i < 8; i++)
            buff[i] = (byte) ((val >> ((7 - i) * 0b1000)) & 0xFF);
        return buff;
    }
    
    private byte[] doubleToBuff(double val) {
        byte[] buff = new byte[8];
        ByteBuffer.wrap(buff).putDouble(val);
        return buff;
    }
    
    
    private byte[] quatToBuff(Quaternion q) {
        byte[][] buffs = new byte[][] {
            doubleToBuff(q.A()),
            doubleToBuff(q.B()),
            doubleToBuff(q.C()),
            doubleToBuff(q.D())
        };

        byte[] buff = new byte[32];
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 8; j++)
                buff[8*i+j] = buffs[i][j];
        return buff;
    }

    private byte[] vectorToBuff(Vector v) {
        byte[][] buffs = new byte[][] {
            doubleToBuff(v.X),
            doubleToBuff(v.Y),
            doubleToBuff(v.Z)
        };

        byte[] buff = new byte[24];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 8; j++)
                buff[8*i+j] = buffs[i][j];
        return buff;
    }
    
    private void writeToBuffer(ArrayList<Byte> buffer, byte[] input) {
        for(Byte b : input)
            buffer.add(b);
    }
    
    /**
     * Loads a model from a file for editing.
     *
     * @param dir The location of the file.
     * @param name The filename, excluding the extension.
     */
    public MultiShape3D loadModel(String dir, String name) {
        return loadModel(dir + name + '.' + MODEL_FILETYPE);
    }

    /**
     * Loads a model from a file for editing.
     *
     * @param file The full file location.
     */
    public MultiShape3D loadModel(String file) {

        try {
            FileInputStream fis = new FileInputStream(file);

            int size = getNextInt(fis); 
            Shape3D[] shapes = new Shape3D[size];

            for (int i = 0; i < size; i++) {
                int parts = getNextInt(fis);
                Vector pos = getNextVector(fis);
                Quaternion rot = getNextQuaternion(fis);

                Plane[] planes = new Plane[parts];
                for (int j = 0; j < parts; j++) {
                    
                    Vector N = getNextVector(fis);
                    double D = getNextDouble(fis);
                    planes[j] = new Plane(N, D);
                }
                
                shapes[i] = new Shape3D(new Polyhedron(planes));
                shapes[i].setTransform(new Transform(pos, rot));

            }

            return loadedModel = new MultiShape3D(shapes);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Saves a model to the specified directory.
     *
     * @param model The model to save.
     */
    public void saveModel(MultiShape3D model, String dir, String name) {
        saveModel(model, dir + name + '.' + MODEL_FILETYPE);   
    }

    public void saveModel(MultiShape3D model, String file) {

        ArrayList<Byte> buffer = new ArrayList<>();
        Shape3D[] shapes = model.getShapes();
        
        writeToBuffer(buffer, intToBuff(shapes.length));
        
        for (int i = 0; i < shapes.length; i++) {
            Polyhedron poly = shapes[i].getPoly();
            Plane[] planes = poly.planes();
            writeToBuffer(buffer, intToBuff(planes.length));
            
            writeToBuffer(buffer, vectorToBuff(shapes[i].getTransform().getPos()));
            writeToBuffer(buffer, quatToBuff(shapes[i].getTransform().getRot()));

            for (int j = 0; j < planes.length; j++) {
                //Add the equation data.
                writeToBuffer(buffer, vectorToBuff(planes[i].N));
                writeToBuffer(buffer, doubleToBuff(planes[i].D));
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(file, false);

            for(int i = 0; i < buffer.size(); i++)
                fos.write((byte) buffer.get(i));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static MultiShape3D newModel() {
        return loadedModel = new MultiShape3D(new Shape3D(new Tetrahedron(1)));
    }

    public static MultiShape3D getLoadedModel() {
        return loadedModel == null ? (loadedModel = newModel()) : loadedModel;
    }
    
    public static void setLoadedModel(MultiShape3D model) { loadedModel = model; }
}


