
import eu.mihosoft.vrl.v3d.parametrics.*;
CSG getNut(){
	String type= "vexFlatSheet"
	if(args==null)
		args=["1x5"]
	StringParameter size = new StringParameter(	type+" Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
	//println "Database loaded "+database
	HashMap<String,Object> servoConfig = Vitamins.getConfiguration( type,size.getStrValue())
  
  
  
	return <part>
		.setParameter(size)
		.setRegenerate({getNut()})
}
return getNut()
