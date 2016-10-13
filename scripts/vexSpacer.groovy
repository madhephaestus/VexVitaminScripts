import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getSpacer(){
	String type= "vexSpacer"
	if(args==null)
		args=["1/8\""]
	StringParameter size = new StringParameter(	type+" Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
	//println "Database loaded "+database
	HashMap<String,Object> vexSpacerConfig = Vitamins.getConfiguration( type,size.getStrValue())
  
  double outerRadius = vexSpacerConfig.get(outerDiameter).toMM()/2
  double innerRadius = vexSpacerConfig.get(innerDiameter).toMM()/2
  
  CSG outside = new Cylinder(outerRadius, outerRadius, vexSpacerConfig.get(thickness).toMM(),30).toCSG() //bottomRadius, topRadius, height, resolution
  CSG inside = new Cylinder(innerRadius, innerRadius, vexSpacerConfig.get(thickness).toMM(), 30).toCSG()
  CSG spacer = outside.difference(inside)
  
  	//outputSheet = outputSheet.union(new Cube(side).toCSG())
	return spacer
		.setParameter(size)
		.setRegenerate({getSpacer()})
}
return getSpacer()
