import java.util.ArrayList;

public class Scene {
    //Source[] sources;
    Rays rays;
    ArrayList<Source> sources = new ArrayList<Source>();
    ArrayList<Shape> shapes = new ArrayList<Shape>();
    double refractiveIndex = 1;

    public void trace(int loopLimit){
        // Traces rays from sources to sensors until no rays are left

        rays = new Rays();
        rays.combineSources(sources);

        for (int i=0;i<loopLimit;i++){ // prevents closed loop bouncing
            if (raysAreActive()){
                traceStep();
            }
        }

    }
    public void traceStep(){
        // executes a single step within the trace. All rays bounce to the next shape.
        rays.createNewBasis();
        Matrix2d distance = new Matrix2d(new int[]{shapes.size(), rays.numRays}); // each ray,shape, distance
        distance.fillWithItem(Double.POSITIVE_INFINITY); // assume ray doesn't intersect with shape
        for (int i=0;i<shapes.size();i++){
            if (shapes.get(i).traceLowRes()){
                distance.vals[i] = shapes.get(i).traceDistance(rays);
            }
        }
        BooleanArray closestShapes = distance.minCol(); // for each ray, which shape intersection is the closest?
        for (int i=0;i<shapes.size();i++){
            if (!closestShapes.allFalseRow(i)){ // skip shapes with no intersection
                rays.update(closestShapes.vals[i],shapes.get(i));
            }
        }

    }
    public boolean raysAreActive(){
        // returns true if some rays are not consumed by a sensor, or boundary
        return true; // give one iteration for now
    }
    public void plot(){
        // plots all objects within the scene onto the canvas for the user to see
        // Plots shapes and rays onto 3D canvas
        // Plots sensor readings onto 2D canvas

        // for now, this function must plot 3 lines onto the 3D canvas
        // I can describe the lines in any way necesary, but for now I assume I
        // need to give the initial point 1 and the second point 2
        // each column is each line, and each row is each dimension x,y,z
        // for example the first lines goes between (0,0,1) and (3,1,0)

        Matrix2d point1 = new Matrix2d(new double[][] {{0,1,5,3,4},{0,0,0,0,0},{1,1,0.5F,0,0}});
        Matrix2d point2 = new Matrix2d(new double[][] {{3,4,2,6,7},{1,1,1,1,1},{0,0,0.5F,1,1}});

        // tip: access matrix values with point1.vals

        // to plot the surface, we plot R^2, plot however you want
        // please use Matrix2d class instead of double[][]
        // I have done this stuff a few times in different languages, so I
        // can help with the process, e.g. grid of points, convert polar to cartesian etc
    }
}