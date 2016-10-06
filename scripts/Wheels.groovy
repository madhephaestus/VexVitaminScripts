import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getNut(){
	String type= "vexWheels.json"
	if(args==null)
		args=["4inchStandard"]
	StringParameter size = new StringParameter(	type+" Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
	//println "Database loaded "+database
	HashMap<String,Object> vexWheelConfig = Vitamins.getConfiguration( type,size.getStrValue())

//boolean type = false 
CSG mainBody = new Cylinder((vexWheelConfig.diameter)/2, // Radius at the bottom
                       		(vexWheelConfig.diameter)/2, // Radius at the top
                       		(vexWheelConfig.totalHeight), // Height
                       		(int)30 //resolution
                       		).toCSG()//convert to CSG to display  

     return mainBody
		.setParameter(size)
		.setRegenerate({getNut()})
}
return getNut()
