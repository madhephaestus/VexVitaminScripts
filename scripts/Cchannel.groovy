import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getFlat() {
    double thickness = 1.6002; //aluminum, steel is 1.1684
    double holeWidth = 4.6609;
    double holeXInset = 4.1, holeYInset = 3.3;
    double pitch = 12.7;
    
    int holeCountLength = 5, holeCountWidth = 1;
    
    CSG plate = new Cube(holeCountLength * pitch, holeCountWidth * pitch - thickness, thickness).toCSG();
    plate = plate.movex(-plate.getMinX()).movey(-plate.getMinY()).movez(-plate.getMinZ());
    
    //Build y holes
    CSG hole = new Cube(holeWidth, holeWidth, thickness).toCSG();
    hole = hole.movex(-hole.getMinX()).movey(-hole.getMinY()).movez(-hole.getMinZ());
    
    CSG yHoles = hole.clone();
    
    //Lined up holes
    for (int i = 1; i < holeCountWidth; i++) {
        yHoles = yHoles.union(hole.movey(i * pitch));
    }
    
    //Holes are inset from the side
    yHoles = yHoles.movey(plate.getMaxY() - yHoles.getMaxY());
    yHoles = yHoles.movex(holeXInset).movey(-holeYInset);
    
    //Build hole set
    CSG holeSet = yHoles.clone();
    
    for (int i = 1; i < holeCountLength; i++) {
        holeSet = holeSet.union(yHoles.movex(i * pitch));
    }
    
    plate = plate.difference(holeSet);
    
    //Center corner at (0, 0)
    plate = plate.movey(-plate.getMinY());
}

CSG getCChannel() {
    double thickness = 1.6002; //aluminum, steel is 1.1684
    double holeWidth = 4.6609, holeThinGap = 1.6764;
    double holeXInset = 4.1, holeYInset = 3.3;
    double pitch = 12.7;
    
    int holeCountLength = 5, holeCountWidth = 2;
    
    CSG plate = new Cube(holeCountLength * pitch, holeCountWidth * pitch - thickness, thickness).toCSG();
    plate = plate.movex(-plate.getMinX()).movey(-plate.getMinY()).movez(-plate.getMinZ());
    
    //Build y holes
    CSG hole = new Cube(holeWidth, holeWidth, thickness).toCSG();
    hole = hole.movex(-hole.getMinX()).movey(-hole.getMinY()).movez(-hole.getMinZ());
    
    CSG yHoles = hole.clone();
    
    //Lined up holes
    for (int i = 1; i < holeCountWidth; i++) {
        yHoles = yHoles.union(hole.movey(i * pitch));
    }
    
    //2 wide has offset holes
    if (holeCountWidth == 2) {
        yHoles = yHoles.union(hole.movey(holeWidth + holeThinGap).movex(holeWidth + holeThinGap));
    }
    
    //Holes are inset from the side
    yHoles = yHoles.movey(plate.getMaxY() - yHoles.getMaxY());
    yHoles = yHoles.movex(holeXInset).movey(-holeYInset);
    
    //Build hole set
    CSG holeSet = yHoles.clone();
    
    for (int i = 1; i < holeCountLength; i++) {
        holeSet = holeSet.union(yHoles.movex(i * pitch));
    }
    
    plate = plate.difference(holeSet);
    
    CSG side = getFlat().rotx(-90).movez(thickness);
    plate = plate.union(side).union(side.movey(plate.getTotalY() + side.getTotalY()));
    
    //Center corner at (0, 0)
    plate = plate.movey(-plate.getMinY());
    
    CSG cornerFill = new Cube(plate.getTotalX(), thickness, thickness).toCSG();
    cornerFill = cornerFill.movex(-cornerFill.getMinX()).movey(-cornerFill.getMinY()).movez(-cornerFill.getMinZ());
    CSG otherCornerFill = cornerFill.movey(plate.getMaxY() - cornerFill.getMaxY());
    plate = plate.union(cornerFill).union(otherCornerFill);
    
    return plate;
}

return getCChannel()
