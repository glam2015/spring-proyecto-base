package py.com.proyectobase.audit;

import org.hibernate.envers.RevisionListener;

import py.com.proyectobase.main.ApplicationContextProvider;
import py.com.proyectobase.main.SesionUsuario;

public class AuditListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		AuditRevEntity auditRevEntity = (AuditRevEntity) revisionEntity;
		SesionUsuario sesioUsuario = (SesionUsuario) ApplicationContextProvider
				.getBeanStatic("sesionUsuario");
		if (sesioUsuario != null && sesioUsuario.getUsuario() != null) {
			auditRevEntity.setUsername(sesioUsuario.getUsuario().getCodigo());
		} else {
			auditRevEntity.setUsername("usernull");
		}
	}

}
