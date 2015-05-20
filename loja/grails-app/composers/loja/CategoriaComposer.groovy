
package loja


import loja.Categoria;
import loja.Produto;

import org.zkoss.zk.ui.Executions
import org.zkoss.zk.ui.event.Event
import org.zkoss.zk.ui.event.EventListener
import org.zkoss.zk.ui.event.EventQueue
import org.zkoss.zk.ui.event.EventQueues
import org.zkoss.zk.ui.event.ForwardEvent
import org.zkoss.zk.ui.event.InputEvent
import org.zkoss.zk.ui.util.Clients
import org.zkoss.zkgrails.*
import org.zkoss.zul.*
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.grails.composer.*
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.select.annotation.*

class CategoriaComposer extends zk.grails.Composer {

	Listbox lstCategorias


	Listbox lstProdutos
	Panel pnlProduto





	def afterCompose = { window -> listar() }




	void listarCategorias(){

		lstCategorias.items.clear()

		if (lstCategorias.heads.size() == 0) {
			lstCategorias.append{
				listhead(sizable:true){
					listheader(label: "ID")
					listheader(label: "Nome")
					listheader()
					listheader()
				}
			}
		}



		lstCategorias.append{

			listitem(){
				listcell(){
					hlayout{ label(value:'ID') }
				}
				listcell(){
					hlayout{
						textbox(placeholder:'Categoria',id:"txtCategoriaNovo")
					}
				}
				listcell(){
					hlayout{
						toolbarbutton(label:'Adicionar',image:"/images/skin/database_add.png",onClick: { e->
							this.salvar();
						})
					}
				}
				listcell()
			}
		}
		lstCategorias.append{

			Categoria.list().each{ categoria ->
				listitem(value:categoria){ item ->
					listcell(label: categoria.id)
					listcell(){
						hlayout{
							textbox(value:categoria.nome_cat,id:"txtCategoria"+categoria.id)
						}
					}

					listcell(){
						hlayout{
							toolbarbutton(label:'Alterar',image:"/images/skin/database_edit.png",onClick:{ e ->
								this.alterar(categoria);
							}
							)
						}
					}

					listcell(){
						hlayout{
							toolbarbutton(label:'Deletar',image:"/images/skin/database_delete.png",onClick:{ e ->
								this.excluir(item);
							}
							)
						}
					}
				}
			}
		}
	}

	void salvar(){


		Textbox txtCategoria = $("#txtCategoriaNovo")[0]

		Categoria cat = new Categoria()
		cat.nome_cat = txtCategoria.value

		if (!cat.hasErrors()){
			cat.save(flush:true)
		}
		else{
			def erros=""
			int i = 1
			cat.errors.allErrors.each{ erro ->
				erros += "\n "+i.toString()+") "+message(error:erro)
				i++
			}
			Messagebox.show("Não foi possível salvar."+erros,"Erro",Messagebox.OK,Messagebox.ERROR)
		}



		listar()
	}

	void excluir(Listitem item){

		Categoria categoria = item.value
		categoria.delete(flush:true)
		item.detach()

		listar()
	}

	void alterar(Categoria cat){
		Textbox txtCategoria = $("#txtCategoria"+cat.id)[0]


		cat.nome_cat = txtCategoria.value

		if (!cat.hasErrors()){
			cat.save(flush:true)
		}
		else{
			def erros=""
			int i = 1
			cat.errors.allErrors.each{ erro ->
				erros += "\n "+i.toString()+") "+message(error:erro)
				i++
			}
			Messagebox.show("Não foi possível salvar."+erros,"Erro",Messagebox.OK,Messagebox.ERROR)
		}


		listar()
	}



	void listar(){
		listarCategorias()

		if (!Categoria.list().isEmpty()){
			listarProdutos()
		}
		else{
			pnlProduto.setVisible(false)
		}
	}















	void listarProdutos(){

		pnlProduto.setVisible(true)

		lstProdutos.items.clear()


		lstProdutos.items.clear()

		if (lstProdutos.heads.size() == 0) {
			lstProdutos.append{
				listhead(sizable:true){
					listheader(label: "ID")
					listheader(label:"Categoria")
					listheader(label:"Nome")
					listheader(label:"Preço")
					listheader(label:"Data de Fabricação")
					listheader()
					listheader()
				}
			}
		}



		lstProdutos.append{

			listitem(){
				listcell(){
					hlayout{ label(value:'ID') }
				}
				listcell(){
					hlayout{
						combobox(id:"cbCategoriaNovo"){
							Categoria.findAll().each{categoria ->
								comboitem(value:categoria,label:categoria.nome_cat)
							}
						}
					}
				}
				listcell(){
					hlayout{
						textbox(placeholder:'Produto',id:"txtProdutoNovo")
					}
				}
				listcell(){
					hlayout{
						textbox(placeholder:'Preço',id:"txtPrecoNovo")
					}
				}
				listcell(){
					hlayout{ datebox(id:"dtbFabNovo") }
				}
				listcell(){
					hlayout{
						toolbarbutton(label:'Adicionar',image:"/images/skin/database_add.png",onClick: { e->
							this.salvarProduto();
						})
					}
				}
				listcell()
			}
		}
		
			Produto.list().each{ produto ->
				lstProdutos.append{
				listitem(value:produto){ item ->
					listcell(label: produto.id)
					listcell(){
						hlayout{
							combobox(id:"cbCategoria"+produto.id){
								Categoria.findAll().each{categoria ->
									comboitem(value:categoria,label:categoria.nome_cat)
								}
							}
						}
					}
					listcell(){
						hlayout{
							textbox(value:produto.nome,id:"txtProduto"+produto.id)
						}
					}
					listcell(){
						hlayout{
							textbox(value:produto.preco,id:"txtPreco"+produto.id)
						}
					}
					listcell(){
						hlayout{
							datebox(value:produto.dataFabricacao,id:"dtbFab"+produto.id)
						}
					}

					listcell(){
						hlayout{
							toolbarbutton(label:'Alterar',image:"/images/skin/database_edit.png",onClick:{ e ->
								this.alterarProduto(produto);
							}
							)
						}
					}

					listcell(){
						hlayout{
							toolbarbutton(label:'Deletar',image:"/images/skin/database_delete.png",onClick:{ e ->
								this.excluirProduto(item);
							}
							)
						}
					}
				}
			}
			Combobox cbCategoria = $("#cbCategoria"+produto.id)[0]
			cbCategoria.selectedItem = cbCategoria.items.find{ it.value.id == produto.categoria.id } 	
		}
	}




	void salvarProduto(){

		Combobox cbCategoriaNovo = $("#cbCategoriaNovo")[0]
		Textbox txtProduto = $("#txtProdutoNovo")[0]
		Textbox txtPreco = $("#txtPrecoNovo")[0]
		Datebox dtbFab  = $("#dtbFabNovo")[0]

		Produto prod = new Produto()
		prod.categoria = cbCategoriaNovo.selectedItem.value
		prod.nome = txtProduto.value

		double precodouble = Double.parseDouble(txtPreco.value)

		prod.preco = precodouble
		prod.dataFabricacao = dtbFab.value

		if (!prod.hasErrors()){
			prod.save(flush:true)
		}
		else{
			def erros=""
			int i = 1
			prod.errors.allErrors.each{ erro ->
				erros += "\n "+i.toString()+") "+message(error:erro)
				i++
			}
			Messagebox.show("Não foi possível salvar."+erros,"Erro",Messagebox.OK,Messagebox.ERROR)
		}



		listar()
	}

	void excluirProduto(Listitem item){

		Produto prod = item.value
		prod.delete(flush:true)
		item.detach()

		listar()
	}

	void alterarProduto(Produto prod){


		Combobox cbCategoria = $("#cbCategoria"+prod.id)[0]		
		Textbox txtProduto = $("#txtProduto"+prod.id)[0]
		Textbox txtPreco = $("#txtPreco"+prod.id)[0]
		Datebox dtbFab = $("#dtbFab"+prod.id)[0]

		prod.categoria = cbCategoria.selectedItem.value		
		prod.nome = txtProduto.value


		double precodouble = Double.parseDouble(txtPreco.value)
		prod.preco = precodouble

		prod.dataFabricacao = dtbFab.value



		if (!prod.hasErrors()){
			prod.save(flush:true)
		}
		else{
			def erros=""
			int i = 1
			prod.errors.allErrors.each{ erro ->
				erros += "\n "+i.toString()+") "+message(error:erro)
				i++
			}
			Messagebox.show("Não foi possível salvar."+erros,"Erro",Messagebox.OK,Messagebox.ERROR)
		}


		listar()
	}
}



