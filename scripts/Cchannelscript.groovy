import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getNut(){
	String type= "vexCchannel"
	if(args==null)
		args=["2x5"]
	StringParameter size = new StringParameter(	type+" Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
	//println "Database loaded "+database
	HashMap<String,Object> vexSheetConfig = Vitamins.getConfiguration( type,size.getStrValue())

	double thickness = 0.8
	double side = 12.5

	double nunRad = ((side/3)/(2*Math.sin(Math.PI/8)))*3/2
	CSG head =new Cylinder(nunRad,nunRad,thickness,(int)8).toCSG() 
				.rotz(45/2)
  
   	CSG sheet = new Cube(	side,// X dimention
			side,// Y dimention
			thickness//  Z dimention
			).toCSG()// this converts from the geometry to an object we can work with
			.intersect(head)
	CSG hole = new Cube(	4.7,// X dimention
			4.7,// Y dimention
			thickness*2//  Z dimention
			).toCSG()// this converts from the geometry to an object we can work with
	
	CSG midhole = new Cube( 4.7,4.7,thickness*2).toCSG()
	
			
  	CSG stub = sheet.difference(hole)
  	CSG outputSheet = stub.clone()
  	if (vexSheetConfig.x == 5)
	{
	for (int i=0;i< vexSheetConfig.x;i++){
  		for (int j=0;j<vexSheetConfig.y;j++){
  			outputSheet = outputSheet.union(
  					stub
  						.movex(i*side)
  						.movey(j*side)
  					
  				)
  		}
  	}
	}
	else
	{
		for (int i=0;i< vexSheetConfig.x;i++){
			for (int j=0;j<vexSheetConfig.y;j++) {
				outputSheet = outputSheet.union(
					stub
  						.movex(i*side)
  						.movey(j*side)
					}
					}
		for (int i=0;i<vexSheetConfig.x;i++){	
			outputSheet.difference 
			
			
  	//outputSheet = outputSheet.union(new Cube(side).toCSG())
	return outputSheet
		.setParameter(size)
		.setRegenerate({getNut()})
}
return getNut()
