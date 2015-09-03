package py.com.proyectobase.controllers;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import py.com.proyectobase.bc.BusinessController;
import py.com.proyectobase.bc.RolBC;
import py.com.proyectobase.bc.RolUsuarioBC;
import py.com.proyectobase.bc.UsuarioBC;
import py.com.proyectobase.dataprovider.DataTableSqlDs;
import py.com.proyectobase.domain.Rol;
import py.com.proyectobase.domain.Usuario;
import py.com.proyectobase.domain.report.UsuarioRep;
import py.com.proyectobase.exception.BusinessLogicException;
import py.com.proyectobase.main.Message;
import py.com.proyectobase.util.Util;
import py.com.proyectobase.viewmodel.RolUsuario;
import py.com.proyectobase.viewmodel.RolesUsuario;
import py.una.cnc.lib.db.dataprovider.DataTableModel;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

@Controller
@Scope("request")
@RequestMapping(value = "/abm/usuario")
public class UsuarioVistaControlador extends ControladorVistaAncestro<Usuario> {

	@Autowired
	private DataTableSqlDs dataTableSqlDs;
	@Autowired
	private UsuarioBC usuarioBC;
	@Autowired
	private RolUsuarioBC rolUsuarioBC;
	@Autowired
	private RolBC rolBC;
	@Autowired
	private Message msg;
	@Autowired
	private Util util;

	@Override
	protected Usuario getNuevaInstanciaBean() {

		Usuario usuario = new Usuario();
		usuario.setFechaUltimoAcceso(new Date());
		return usuario;
	}

	@Override
	public BusinessController<Usuario> getBusinessController() {

		return usuarioBC;
	}

	@Override
	public String getAbmUrl() {

		return "abm/usuario/index";
	}

	@Override
	public String guardar(Usuario bean, final BindingResult result,
			final ModelMap model) {

		return super.guardar(bean, result, model);
	}

	@RequestMapping("/registrar")
	public String registrar(ModelMap model,
			@RequestParam("codusuario") String codusuario) {

		String url = nuevoRegistro(model);
		Usuario usuario = (Usuario) model.get(getNombreObjeto());
		usuario.setCodigo(codusuario);
		model.addAttribute(getNombreObjeto(), usuario);
		return url;
	}

	@Override
	public String[] getTableColumns() {

		return new String[] { "id", "codigo", "nombre", "apellido", "email" };
	}

	@Override
	public String[] getReportColumns() {

		return new String[] { "rownum", "codigo", "nombre", "apellido", "email" };
	}

	/**
	 * Sobreescribo cuando edito porque debo ocultar la contraseña, setear a
	 * null
	 */
	@Override
	protected Usuario getRegistroEnEdicion(Usuario usuario, ModelMap model) {

		usuario.setPassword(null);

		return usuario;
	}

	@Override
	protected Usuario onGuardarRegistroOk(Usuario usuario, ModelMap model) {

		usuario.setPassword(null);
		return usuario;
	}

	@RequestMapping(value = "roles")
	public String getRolesl(ModelMap modelMap, @RequestParam Long usuarioId) {

		Usuario usuario = usuarioBC.find(usuarioId);

		if (usuario == null) {
			usuario = new Usuario();
		}

		RolesUsuario rolesUsuario = new RolesUsuario();
		rolesUsuario.setUsuario(usuario);
		rolesUsuario.setUsuarioId(usuarioId);
		rolesUsuario.setRolUsuarioList(dataTableSqlDs.getRolesUsuario(usuario));

		modelMap.addAttribute("rolesUsuario", rolesUsuario);
		modelMap.addAttribute("usuario", usuario);

		return "abm/usuario/rol_usuario_picker";
	}

	@RequestMapping(value = "actualizar_roles", method = RequestMethod.POST)
	public String actualizarRoles(ModelMap modelMap, RolesUsuario rolesUsuario) {

		Usuario usuario = usuarioBC.find(rolesUsuario.getUsuarioId());

		getLogger().info("Actualizando permisos de usuarioId: {}",
				rolesUsuario.getUsuarioId());
		for (RolUsuario ru : rolesUsuario.getRolUsuarioList()) {
			Rol rol = rolBC.find(ru.getRolid());
			if (ru.getSelected()) {
				rolUsuarioBC.agregar(rol, usuario);
			} else {

				rolUsuarioBC.quitar(rol, usuario);
			}
			getLogger().info("crolid: {}, selected: {}", ru.getRolid(),
					ru.getSelected());
		}
		return getRolesl(modelMap, rolesUsuario.getUsuarioId());
	}

	@Override
	protected DataTableModel<?> getDataTable(Integer iDisplayStart,
			Integer iDisplayEnd, String orderBy, String sSearch) {

		DataTableModel<?> usuarioDT = dataTableSqlDs.getDataTable(
				iDisplayStart, iDisplayEnd, orderBy, sSearch, "select_usuario",
				"filter_usuario", DataTableSqlDs.WITHOUT_WHERE,
				UsuarioRep.class);
		return usuarioDT;
	}

	@Override
	protected AbstractColumn getReportColumn(String colName, String colTitle,
			Style detailStyle, Style headerStyle) {

		Class<?> columnClass = String.class;
		int width = 100;
		if (colName.compareTo("rownum") == 0) {
			columnClass = Long.class;
			width = 30;
		}

		AbstractColumn column = ColumnBuilder.getNew()
				.setColumnProperty(colName, columnClass).setWidth(width)
				.setTitle(colTitle).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		return column;

	}

	@Override
	protected String uploadSuccessReturnPage(ModelMap model,
			MultipartFile file, Long recordId) {

		Usuario usuario = null;
		try {
			byte[] bytes = file.getBytes();
			getLogger().info("foto: {}, type: {}", file.getOriginalFilename(),
					file.getContentType());

			usuario = usuarioBC.find(recordId);

			if (!file.getContentType().contains("image")) {
				setMensajeError(msg.get("usuario.foto.contenttype.error"));
				return edicionRegistro(model, usuario);
			}

			usuario.setFoto(bytes);
			usuarioBC.edit(usuario);
			setMensajeOk(msg.get("usuario.foto.cambiada"));
			return edicionRegistro(model, usuario);

		} catch (Exception exc) {
			getLogger().error("No se pudo cambiar foto de usuario: {}", exc,
					usuario);
			setMensajeError(msg.get("usuario.foto.error"));
			return index(null, model);
		}

	}

	@RequestMapping(value = "foto/{recordId}")
	public ResponseEntity<byte[]> getFoto(@PathVariable Long recordId) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		String filename = recordId + ".png";

		headers.add("content-disposition", "inline; filename=" + filename);
		Usuario usuario = usuarioBC.find(recordId);

		byte[] foto = usuario.getFoto();
		if (foto == null) {
			try {

				String path = null;
				if (usuario.getEsMasculino()) {
					path = "/images/male_user.png";
				} else {
					path = "/images/female_user.png";
				}

				Resource resource = new ClassPathResource(path);
				InputStream resourceInputStream = resource.getInputStream();

				foto = IOUtils.toByteArray(resourceInputStream);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		}

		return new ResponseEntity<byte[]>(foto, headers, HttpStatus.OK);

	}

	@Override
	@ModelAttribute("modulo")
	public String getNombreModulo() {

		return "administracion";
	}

	@Override
	protected Usuario getRegistroPreGuardado(Usuario usuario, ModelMap model,
			BindingResult result) {

		Usuario tmp = usuarioBC.find(usuario.getId());
		/**
		 * Si se edita la contraseña, entonces viene sin encriptar. Si viene
		 * null o vacía, entonces no se modificó contraseña y debemos recuperar
		 * la antigua, ya que para no mostrar la contraseña en la vista, se puso
		 * usuario.setPassword(null)
		 */
		if (usuario.getPassword() == null
				|| usuario.getPassword().trim().length() == 0) {

			if (tmp != null) {
				usuario.setPassword(tmp.getPassword());
			}
		} else {

			try {
				String password = util.getMD5(usuario.getPassword());
				usuario.setPassword(password);
			} catch (NoSuchAlgorithmException exc) {
				throw new BusinessLogicException(exc.getMessage(), exc);
			}
		}

		/**
		 * Cuando se guarda desde un formulario, no se envía la foto del
		 * usuario, por lo tanto recuperamos la foto actual para volver a setear
		 */

		if (tmp != null) {
			usuario.setFoto(tmp.getFoto());
		}
		return usuario;
	}

}
