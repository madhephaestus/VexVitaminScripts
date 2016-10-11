import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getWheel(){
	String type= "vexWheels"
	if(args==null)
		args=["4inchStandard"]
	StringParameter size = new StringParameter(	type+" Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
	//println "Database loaded "+database
	HashMap<String,Object> vexWheelConfig = Vitamins.getConfiguration( type,size.getStrValue())
	boolean roundEdge = false 
	if(vexWheelConfig.squareOrRound.equals("round"))
	{
		 roundEdge = true 
	}
	//println roundEdge
	CSG mainBody = new Cylinder((vexWheelConfig.diameter)/2, // Radius at the bottom
    	                   		(vexWheelConfig.diameter)/2, // Radius at the top
                       		(vexWheelConfig.totalHeight), // Height
                       		(int)40 //resolution
                       		).toCSG()//convert to CSG to display  
      //does this is the wheel is "round" at the edge
      if(roundEdge){
      	CSG tempBody = mainBody.movex(vexWheelConfig.diameter)
      	CSG subtractSphere = new Sphere((vexWheelConfig.diameter)/2)
      								.toCSG()
      	subtractSphere = subtractSphere.rotx(90)
      							 .movex(vexWheelConfig.diameter)
      							 .movez(vexWheelConfig.totalHeight/2)
      	tempBody = tempBody.difference(subtractSphere)
      	
      	tempBody = tempBody.difference(subtractSphere)
      	mainBody = mainBody.difference(tempBody.movex(-vexWheelConfig.diameter))
      }
     double axleWidth = 5
     CSG subtractCube = new Cube(axleWidth,
     					   axleWidth,
     					   vexWheelConfig.totalHeight)
     					   .toCSG()

     mainBody = mainBody.difference(subtractCube.toZMin())
     return mainBody
		.setParameter(size)
		.setRegenerate({getWheel()})
}
return getWheel()
