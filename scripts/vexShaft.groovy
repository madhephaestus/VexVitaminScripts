
import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getStandoff(){
	String type= "vexShaft"
	if(args==null)
		args=["0.5 inch"]
	StringParameter size = new StringParameter(	type+" Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
                    
	HashMap<String, Object> vexStandoffConfig = Vitamins.getConfiguration(type, size.getStrValue())
	double length = vexStandoffConfig.length
	double width = (1.0/4.0)*25.4
  
  CSG standoff = new Cube(width, width, length).toCSG().toZMin()

  return standoff
     .setParameter(size)
		.setRegenerate({getStandoff()})
}
return getStandoff()
