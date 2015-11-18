package com.web.faces.listeners;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.web.pet.bean.MenuBean;
import com.web.pet.bean.UsuarioBean;
import com.web.pet.pojo.annotations.Menu;
import com.web.pet.pojo.annotations.Sevmenu;
import com.web.util.FacesUtil;

public class SecurityPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3917092198608197271L;

	public SecurityPhaseListener() {
	}
	
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;//PhaseId.ANY_PHASE;
	}
	
	public void afterPhase(PhaseEvent phaseEvent) {
		if(phaseEvent.getPhaseId() == PhaseId.RESTORE_VIEW){
			UsuarioBean usuarioBean = (UsuarioBean) new FacesUtil().getSessionBean("usuarioBean");
			FacesContext facesContext = phaseEvent.getFacesContext();
			
			//si ingresa a login y ya esta logoneado redirecciona a home
			String vista = facesContext.getViewRoot().getViewId();
			boolean loginPage = vista != null && vista.equals("/pages/login.xhtml");
			if(loginPage){
				if(usuarioBean != null && usuarioBean.isAutenticado()){
					try{
						facesContext.getExternalContext().redirect("../admin/home.jsf?faces-redirect=true&iditem=35");
						return;
					}catch(Exception e){}
				}
			}else{
				FacesUtil facesUtil = new FacesUtil();
				MenuBean menuBean = (MenuBean)facesUtil.getSessionBean("menuBean");
				
				if(menuBean == null){
					menuBean = new MenuBean();
					facesUtil.setSessionBean("menuBean", menuBean);
				}
				
				if(menuBean != null){
					int ordenmenupadre = 0;
					String iditem = facesContext.getExternalContext().getRequestParameterMap().get("iditem");
					
					if(iditem != null && Integer.parseInt(iditem) >= 0){
						boolean existepagina = false;
						for(Menu menupadre : menuBean.getLisMenu()){
							//ordenmenupadre = menupadre.getOrden();
							
							if(Integer.parseInt(iditem) == menupadre.getIdmenu()){
								existepagina = true;
								break;
							}
							
							for(Sevmenu menuhijo : menupadre.getLisSevOpcionMenu()){
								if(Integer.parseInt(iditem) == menuhijo.getIdmenu()){
									existepagina = true;
									break;
								}
							}
							if(existepagina){
								break;
							}
							ordenmenupadre++;
						}
						if(existepagina){
							menuBean.setActiveIndex(ordenmenupadre);
							menuBean.setActiveIdItem(Integer.parseInt(iditem));
						}
					}
				}
			}
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {
		/*FacesContext ctx = phaseEvent.getFacesContext();
		HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();
		ValueBinding vb = ctx.getApplication().createValueBinding("#{navigationBean}");
		NavigationBean nb = (NavigationBean)vb.getValue(ctx);
		String path = request.getPathInfo();
		if("/controller".equals(path)){
			try{
				String newPageViewId = null;
				if("mascotas".equalsIgnoreCase(nb.getTarget()))
					newPageViewId = "/mascotas.jsf";
				if("agenda".equalsIgnoreCase(nb.getTarget()))
					newPageViewId = "/agenda.jsf";
				if("colores".equalsIgnoreCase(nb.getTarget()))
					newPageViewId = "/colores.jsf";
				
				UIViewRoot newPage = ctx.getApplication().getViewHandler().createView(ctx, newPageViewId);
				ctx.setViewRoot(newPage);
				ctx.renderResponse();
			}catch(Exception e){
				
			}
		}*/
	}
}
