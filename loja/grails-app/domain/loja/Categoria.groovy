package loja

class Categoria {
		
	String nome_cat
		
	static hasMany = [produtos:Produto]

    static constraints = {
		nome_cat nullable:false, blank: false 
    }
	
	String toString(){
		nome_cat
	}
}