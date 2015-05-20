package loja

class Produto {
	
	String nome
	double preco
	Date dataFabricacao
	Categoria categoria
	
	static belongsTo = [categoria:Categoria]
	
    static constraints = {
		
		nome nullable:false
		dataFabricacao nullable:false
    }
	
	
}

















