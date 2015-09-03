package py.com.proyectobase.controllers;

import java.beans.Introspector;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import py.com.proyectobase.bc.BusinessController;
import py.com.proyectobase.domain.Usuario;
import py.com.proyectobase.exception.ObjectErrorException;
import py.com.proyectobase.main.Message;
import py.com.proyectobase.main.SesionUsuario;
import py.com.proyectobase.report.TablaAbmReporte;
import py.com.proyectobase.util.Util;
import py.una.cnc.lib.core.util.AppLogger;
import py.una.cnc.lib.db.dataprovider.DataTableModel;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

public abstract class ControladorVistaAncestro<M extends py.com.proyectobase.domain.Model> {

	public static final String OPERACION_PARAM = "operacion";
	public static final String BORRAR_PARAM = "borrar";
	public static final String CREAR_PARAM = "crear";
	public static final String GUARDAR_PARAM = "guardar";
	public static final String CANCELAR_PARAM = "cancelar";
	public static final boolean BOTON_CREAR_HABILITADO = true;
	public static final boolean BOTON_GUARDAR_HABILITADO = true;
	public static final boolean BOTON_CANCELAR_HABILITADO = true;
	public static final boolean BOTON_BORRAR_HABILITADO = true;

	public static final String LISTADO = "LISTADO";
	public static final String REGISTRO_GUARDADO = "form.registro_guardado";
	public static final String REGISTRO_BORRADO = "form.registro_borrado";
	public static final String AGREGAR_REGISTRO = "form.registro_completar";
	public static final String MODIFICAR_REGISTRO = "form.registro_editando";
	public static final String SIN_PERMISO_AGREGAR_REGISTRO = "form.sin_permiso_agregar_registro";
	public static final String SIN_PERMISO_VER_REGISTRO = "form.sin_permiso_ver_registro";

	public static int ALL_ROWS = 0;
	private boolean botonCrearHabilitado;
	private boolean botonGuardarHabilitado;
	private boolean botonCancelarHabilitado;
	private boolean botonBorrarrHabilitado;

	private Boolean hasPermissionToUpdate;
	private Boolean hasPermissionToInsert;
	private Boolean hasPermissionToSelect;
	private Boolean hasPermissionToDelete;
	private boolean accionCancelar;
	private boolean accionCrear;
	private boolean accionGuardar;
	private boolean accionBorrar;

	private String mensajeOk;
	private String info;
	private String mensajeAtencion;
	private String mensajeError;

	private List<M> listado;
	private String nombreObjeto;
	@Autowired
	private Message msg;

	@Autowired
	private SesionUsuario sesionUsuario;

	private boolean listadoActualizable;
	@Autowired
	private Util util;
	private String subTituloReporte;

	protected AppLogger logger = new AppLogger(this.getClass().getName());
	private TablaAbmReporte tablaAbmReporte;

	@RequestMapping()
	public String index(M bean, ModelMap model) {

		if (hasPermissionToInsert()) {
			return nuevoRegistro(model);
		} else {
			model.addAttribute(getNombreObjeto(), getNuevaInstanciaBean());
			addGlobalParams(model);
			return getAbmUrl();
		}
	}

	@RequestMapping(value = "op", params = OPERACION_PARAM)
	public String procesarForm(@Valid final M bean, final BindingResult result,
			final ModelMap model,
			@RequestParam(OPERACION_PARAM) String operacionParam) {

		if (operacionParam.compareTo(CREAR_PARAM) == 0) {
			return nuevoRegistro(model);
		} else if (operacionParam.compareTo(GUARDAR_PARAM) == 0) {
			return guardar(bean, result, model);
		} else if (operacionParam.compareTo(BORRAR_PARAM) == 0) {
			return borrarRegistro(model, bean);
		} else if (operacionParam.compareTo(CANCELAR_PARAM) == 0) {
			if (bean != null && bean.getId() != null) {
				return edicionRegistro(model, bean.getId());
			} else {
				return nuevoRegistro(model);
			}
		} else
			return nuevoRegistro(model);
	}

	@RequestMapping(value = "{id}")
	public String edicionRegistro(ModelMap model, @PathVariable Long id) {

		if (!hasPermissionToSelect()) {
			getLogger().error("No tiene permisos para ver registro");
			setMensajeError(msg.get(SIN_PERMISO_VER_REGISTRO));
			return index(null, model);
		}

		M bean = getBusinessController().find(id);
		if (bean == null) {
			return nuevoRegistro(model);
		}

		return edicionRegistro(model, bean);
	}

	public String edicionRegistro(ModelMap model, M bean) {

		onBotonEditarPresionado();
		bean = getRegistroEnEdicion(bean, model);

		if (hasPermissionToUpdate()) {
			setInfo(msg.get(MODIFICAR_REGISTRO));
		} else {
			getLogger().info("No tiene permisos para actualizar registro");
		}
		model.addAttribute(getNombreObjeto(), bean);
		addGlobalParams(model);
		return getAbmUrl();

	}

	public String nuevoRegistro(ModelMap model) {

		getLogger().info("Habilitando vista para carga de nuevo registro");

		if (hasPermissionToInsert()) {

			onBotonCrearPresionado();

			model.addAttribute(getNombreObjeto(), getNuevaInstanciaBean());
			setInfo(msg.get(AGREGAR_REGISTRO));
		} else {
			getLogger().error("No tiene permisos para agregar registro");
			setMensajeError(msg.get(SIN_PERMISO_AGREGAR_REGISTRO));
		}
		addGlobalParams(model);
		return getAbmUrl();
	}

	public String borrarRegistro(ModelMap model, M bean) {

		if (!hasPermissionToDelete()) {
			getLogger().error("No tiene permisos para borrar registro");
			return index(bean, model);
		}

		try {
			getBusinessController().destroy(bean);
			setMensajeOk(msg.get(REGISTRO_BORRADO));
			if (listadoActualizable) {
				actualizarListado();
			}
			return index(null, model);
		} catch (Exception exception) {
			onBorrarFallido();
			handleException(exception);
			model.addAttribute(getNombreObjeto(), bean);
			onBotonEditarPresionado();
			return index(bean, model);
		}
	}

	public String guardar(M bean, final BindingResult result,
			final ModelMap model) {

		if (!(hasPermissionToUpdate() || hasPermissionToInsert())) {
			getLogger().error("No tiene permisos para guardar registro");
			return index(bean, model);
		}

		bean = getRegistroPreGuardado(bean, model, result);
		// Suponemos que es guardar nuevo registro
		if (!result.hasErrors()) {
			try {

				if (bean.getId() == null) {

					getBusinessController().create(bean);

				} else {
					getBusinessController().edit(bean);
				}
				setMensajeOk(msg.get(REGISTRO_GUARDADO));
				bean = onGuardarRegistroOk(bean, model);
				onBotonEditarPresionado();

			} catch (ObjectErrorException errors) {
				for (ObjectError error : errors.getErrors()) {
					result.addError(error);
				}
				getLogger().error("Errores de validación: {}",
						result.getAllErrors());
				onGuardarFallido(bean, model);
			} catch (DataAccessException dae) {
				logger.info(
						"************************************ DataAccessException: {}",
						dae.getMessage());
				handleException(dae);
				onGuardarFallido(bean, model);
			} catch (Exception exception) {
				handleException(exception);
				onGuardarFallido(bean, model);
			}
		} else {

			getLogger().error("Errores de validación: {}",
					result.getAllErrors());
			onGuardarFallido(bean, model);
		}
		addGlobalParams(model);
		return getAbmUrl();
	}

	protected M getRegistroPreGuardado(M bean, ModelMap model,
			BindingResult result) {

		return bean;
	}

	protected M onGuardarRegistroOk(M bean, ModelMap model) {

		return bean;
	}

	/** El registro que se esta editando */
	protected M getRegistroEnEdicion(M bean, ModelMap model) {

		return bean;
	}

	public String getNuevoRegistroUrl() {

		return getAbmUrl();
	}

	public String getEdicionRegistroUrl() {

		return getAbmUrl();
	}

	public abstract String getAbmUrl();

	/** @return retorna una instancia que será guardada en la bd */
	protected abstract M getNuevaInstanciaBean();

	protected void onGuardarFallido(M bean, ModelMap model) {

		onBotonCrearPresionado();
	}

	protected void onBorrarFallido() {

	}

	/** Retorna el controlador del negocio para el formulario */
	public abstract BusinessController<M> getBusinessController();

	protected void actualizarListado() {

		listado = getBusinessController().findEntities();
	}

	public List<M> getListado() {

		if (listado == null) {
			listado = getBusinessController().findEntities();
		}
		return listado;
	}

	public void setListado(List<M> listado) {

		this.listado = listado;
	}

	protected void addGlobalParams(ModelMap model) {

	}

	public void setListadoActualizable(boolean listadoActualizable) {

		this.listadoActualizable = listadoActualizable;
	}

	@ModelAttribute("usuarioActual")
	public Usuario getUsuarioActual() {

		return sesionUsuario.getUsuario();
	}

	@ModelAttribute("sesionUsuario")
	public SesionUsuario getSesionUsuario() {

		return sesionUsuario;
	}

	@ModelAttribute("controlador")
	public ControladorVistaAncestro<M> getControlador() {

		return this;
	}

	protected void handleException(Exception exception) {

		ConstraintViolationException cve = getConstraintViolationException(exception);
		if (cve != null) {

			handleConstraintViolationException(cve);
		} else {

			setMensajeError(exception.getMessage());
		}
	}

	protected ConstraintViolationException getConstraintViolationException(
			Throwable throwable) {

		while (throwable != null) {

			if (throwable instanceof ConstraintViolationException) {
				ConstraintViolationException cve = (ConstraintViolationException) throwable;
				return cve;
			}
			throwable = throwable.getCause();
		}
		return null;
	}

	protected void handleConstraintViolationException(
			ConstraintViolationException cve) {

		setMensajeError(msg.get(cve.getConstraintName()));
		logger.error("ConstraintViolationException: {}", cve.getSQLException()
				.getMessage());
	}

	/*
	 * A veces cuando se cargan registro, se puede presionar F5, entonces se
	 * invoca a este método. Esto es porque el formulario se carga en URL
	 * abm/op, pero es POST y por lo tanto un GET va a dar error
	 */
	@RequestMapping(value = "op")
	public String refresh(final M bean, final ModelMap model) {

		return index(bean, model);
	}

	/**
	 * @return el nombre del rol necesario para realizar acción. Si se desea
	 *         insertar un registro, la acción es ins y el rol necesario es
	 *         'nombre_entidad_ins'. Ej: usuario_ins es el rol necesario para
	 *         insertar un registro
	 */
	public String getRequiredRole(String action) {

		return getPermissionBaseName().concat(action);
	}

	public String getPermissionBaseName() {

		return getBusinessController().getDAOInstance().getEntityName();
	}

	public boolean isFieldDisabled(String field) {

		return !(hasPermissionToInsert() || hasPermissionToUpdate());
	}

	public boolean isBotonCrearHabilitado() {

		return botonCrearHabilitado;
	}

	public void setBotonCrearHabilitado(boolean botonCrearHabilitado) {

		if (botonCrearHabilitado) {
			this.botonCrearHabilitado = hasPermissionToInsert();
		} else {
			this.botonCrearHabilitado = botonCrearHabilitado;
		}
	}

	public boolean isBotonGuardarHabilitado() {

		return botonGuardarHabilitado;
	}

	/**
	 * En el caso que los eventos del formulario permita guardar(por ejemplo al
	 * presionar el botón nuevo), se debe verificar si el usuario tiene permisos
	 * para guardar en BD
	 */
	public void setBotonGuardarHabilitado(boolean botonGuardarHabilitado) {

		if (botonGuardarHabilitado) {
			this.botonGuardarHabilitado = hasPermissionToUpdate()
					|| hasPermissionToInsert();
		} else {
			this.botonGuardarHabilitado = botonGuardarHabilitado;
		}
	}

	public boolean isBotonBorrarHabilitado() {

		return botonBorrarrHabilitado;
	}

	public void setBotonBorrarHabilitado(boolean botonBorrarrHabilitado) {

		if (botonBorrarrHabilitado) {
			this.botonBorrarrHabilitado = hasPermissionToDelete();
		} else {

			this.botonBorrarrHabilitado = botonBorrarrHabilitado;
		}
	}

	public boolean isBotonCancelarHabilitado() {

		return botonCancelarHabilitado;
	}

	public void setBotonCancelarHabilitado(boolean botonCancelarHabilitado) {

		if (botonCancelarHabilitado) {
			this.botonCancelarHabilitado = hasPermissionToUpdate()
					|| hasPermissionToInsert();
		} else {
			this.botonCancelarHabilitado = botonCancelarHabilitado;
		}
	}

	public boolean isAccionCancelar() {

		return accionCancelar;
	}

	public boolean isAccionBorrar() {

		return accionBorrar;
	}

	public boolean isAccionCrear() {

		return accionCrear;
	}

	public boolean isAccionGuardar() {

		return accionGuardar;
	}

	/*
	 * al hacer click en botón crear, se deben habilitar los botones Guardar y
	 * Cancelar. Se deben ocultar los botones Crear y Borrar
	 */
	protected void onBotonCrearPresionado() {

		setBotonCrearHabilitado(!BOTON_CREAR_HABILITADO);
		setBotonCancelarHabilitado(BOTON_CANCELAR_HABILITADO);
		setBotonGuardarHabilitado(BOTON_GUARDAR_HABILITADO);
		setBotonBorrarHabilitado(!BOTON_BORRAR_HABILITADO);
	}

	/*
	 * al hacer click en editar, se pueden habilitar todos los botones, depende
	 * si tiene permisos el usuario
	 */
	protected void onBotonEditarPresionado() {

		setBotonCrearHabilitado(BOTON_CREAR_HABILITADO);
		setBotonCancelarHabilitado(BOTON_CANCELAR_HABILITADO);
		setBotonGuardarHabilitado(BOTON_GUARDAR_HABILITADO);
		setBotonBorrarHabilitado(BOTON_BORRAR_HABILITADO);
	}

	public String getMensajeOk() {

		return mensajeOk;
	}

	public void setMensajeOk(String mensajeOk) {

		this.mensajeOk = mensajeOk;
	}

	public String getMensajeAtencion() {

		return mensajeAtencion;
	}

	public void setMensajeAtencion(String mensajeAtencion) {

		this.mensajeAtencion = mensajeAtencion;
	}

	public String getMensajeError() {

		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {

		this.mensajeError = mensajeError;
	}

	public String getInfo() {

		return info;
	}

	public void setInfo(String info) {

		this.info = info;
	}

	public boolean hasPermissionToInsert() {

		if (hasPermissionToInsert == null) {
			hasPermissionToInsert = sesionUsuario
					.hasRole(getRequiredRole("_ins"));
		}
		return hasPermissionToInsert;
	}

	public boolean hasPermissionToUpdate() {

		if (hasPermissionToUpdate == null) {
			hasPermissionToUpdate = sesionUsuario
					.hasRole(getRequiredRole("_upd"));
		}
		return hasPermissionToUpdate;
	}

	public boolean hasPermissionToDelete() {

		if (hasPermissionToDelete == null) {
			hasPermissionToDelete = sesionUsuario
					.hasRole(getRequiredRole("_del"));
		}
		return hasPermissionToDelete;
	}

	public boolean hasPermissionToSelect() {

		if (hasPermissionToSelect == null) {
			hasPermissionToSelect = sesionUsuario
					.hasRole(getRequiredRole("_sel"));
		}
		return hasPermissionToSelect;
	}

	protected AppLogger getLogger() {

		return logger;
	}

	/**
	 * En un form se guardan datos de un objeto de una determinada clase. Si el
	 * nombre de la clase es Miembro, entonces th:object es miembro
	 */
	public String getNombreObjeto() {

		if (nombreObjeto == null) {
			nombreObjeto = getBusinessController().getDAOInstance()
					.getEntityName();
			nombreObjeto = Introspector.decapitalize(nombreObjeto);
		}
		return nombreObjeto;
	}

	public abstract String[] getTableColumns();

	/**
	 * Retorna las columnas de la tabla separadas por ';' Ej:
	 * id;codigo;nombre;apellido
	 */
	public String getTableColumnsStr() {

		StringBuilder cols = new StringBuilder();
		String[] columns = getTableColumns();
		int len = columns.length;
		for (int index = 0; index < len - 1; index++) {
			cols.append(columns[index]).append(";");
		}
		cols.append(columns[len - 1]);

		return cols.toString();
	}

	@RequestMapping(value = "suggest", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody List<?> suggest(
			@RequestParam(value = "iDisplayStart", required = false, defaultValue = "0") Integer iDisplayStart,
			@RequestParam(value = "iDisplayLength", required = false ) Integer iDisplayLength,
			@RequestParam(value = "iSortCol_0", required = false, defaultValue = "0") Integer orderColumnIndex,
			@RequestParam(value = "sSearch", required = false) String sSearch,
			Model model) {

		String orderBy = getOrderBy(orderColumnIndex);
		return getDataTable(iDisplayStart + 1, iDisplayStart + iDisplayLength,
				orderBy, sSearch).getAaData();
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "rest", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody DataTableModel<?> getDataTableRest(
			@RequestParam(value = "iDisplayStart", required = false, defaultValue = "0") Integer iDisplayStart,
			@RequestParam(value = "iDisplayLength", required = false, defaultValue = "10") Integer iDisplayLength,
			@RequestParam(value = "sEcho", required = false) String sEcho,
			@RequestParam(value = "sSearch", required = false) String sSearch,
			@RequestParam(value = "iSortCol_0", required = false, defaultValue = "0") Integer orderColumnIndex,
			@RequestParam(value = "sSortDir_0", required = false, defaultValue = "asc") String orderDir,

			Model model) throws IOException {

		String orderBy = getOrderBy(orderColumnIndex);
		orderBy = orderBy.concat(" ").concat(orderDir);
		try {
			return getDataTableModel(iDisplayStart + 1, iDisplayStart
					+ iDisplayLength, orderBy, sSearch);
		} catch (Exception exc) {
			return new DataTableModel();
		}

	}

	private String getOrderBy(Integer orderColumnIndex) {

		String[] columns = getTableColumns();
		String orderBy = null;
		if (orderColumnIndex >= 0 && orderColumnIndex < columns.length) {
			orderBy = columns[orderColumnIndex];
		} else {
			orderBy = columns[0];
		}

		return orderBy;
	}

	public DataTableModel<?> getDataTableModel(Integer iDisplayStart,
			Integer iDisplayEnd, String orderBy, String sSearch) {

		DataTableModel<?> dataTable = getDataTable(iDisplayStart, iDisplayEnd,
				orderBy, sSearch);

		return dataTable;

	}

	protected Collection<?> getReportData(String orderBy, String filterValue) {

		DataTableModel<?> dataTable = getDataTable(ALL_ROWS, ALL_ROWS, orderBy,
				filterValue);

		return dataTable.getAaData();
	}

	protected DataTableModel<?> getDataTable(Integer iDisplayStart,
			Integer iDisplayEnd, String orderBy, String sSearch) {

		DataTableModel<M> dataTable = new DataTableModel<M>();

		List<M> list = getBusinessController().findEntities();
		Long size = Long.valueOf(list.size());
		dataTable.setRecordsTotal(size);
		dataTable.setRecordsFiltered(size);
		dataTable.setAaData(list);
		return dataTable;
	}

	@RequestMapping(value = "getpdf")
	public ResponseEntity<byte[]> getPdf(
			@RequestParam(value = "sSearch", required = false) String sSearch,
			@RequestParam(value = "iSortCol_0", required = false, defaultValue = "0") Integer orderColumnIndex,
			@RequestParam(value = "sSortDir_0", required = false, defaultValue = "asc") String orderDir) {

		String orderBy = getOrderBy(orderColumnIndex);
		getTablaAbmReporte().setData(getReportData(orderBy, sSearch));
		getTablaAbmReporte().setLogoPath(getReporteLogoPath());
		getTablaAbmReporte().setTitulo(getTituloReporte());

		if (sSearch != null && sSearch.compareTo("") != 0) {
			subTituloReporte = msg.get("filtro_busqueda") + ": " + sSearch;
		}
		getTablaAbmReporte().setSubTitulo(getSubTituloReporte());
		getTablaAbmReporte().setColumns(getColumnasReporte());
		getTablaAbmReporte().setFechaCreacion(getFechaCreacionReporte());
		getTablaAbmReporte().setUsuarioInfo(getUsuarioInfoReporte());

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		JRPdfExporter exporter = new JRPdfExporter();

		try {
			getTablaAbmReporte().generar();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT,
					getTablaAbmReporte().getJasperPrint());
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

			exporter.exportReport();
		} catch (Exception e) {
			getLogger()
					.error("Error al generar reporte: {}", e.getMessage(), e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = getTituloReporte() + ".pdf";

		headers.add("content-disposition", "inline; filename=" + filename);

		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
				baos.toByteArray(), headers, HttpStatus.OK);
		return response;
	}

	protected List<AbstractColumn> getColumnasReporte() {

		List<AbstractColumn> columns = new ArrayList<>();

		Style headerStyle = getTablaAbmReporte().getReportStyle()
				.getHeaderStyle();

		Style detailStyle = getTablaAbmReporte().getReportStyle()
				.getDetailStyle();

		for (String colName : getReportColumns()) {
			String colTitle;
			if (colName.compareTo("rownum") != 0) {
				colTitle = msg.get(getNombreObjeto() + "_" + colName
						+ "_header");
			} else {
				colTitle = msg.get("ROWNUM");
			}
			AbstractColumn column = getReportColumn(colName, colTitle,
					detailStyle, headerStyle);
			if (column != null)
				columns.add(column);
		}
		return columns;
	}

	protected AbstractColumn getReportColumn(String colName, String colTitle,
			Style detailStyle, Style headerStyle) {

		AbstractColumn column = ColumnBuilder.getNew()
				.setColumnProperty(colName, Object.class.getName())
				.setTitle(colTitle).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		return column;

	}

	public String[] getReportColumns() {

		return getTableColumns();
	}

	protected TablaAbmReporte getTablaAbmReporte() {

		if (tablaAbmReporte == null) {
			tablaAbmReporte = new TablaAbmReporte();
		}
		return tablaAbmReporte;
	}

	protected String getTituloReporte() {

		return msg.get("titulo_reporte_" + getNombreObjeto());
	}

	protected String getSubTituloReporte() {

		if (subTituloReporte == null) {
			subTituloReporte = msg
					.get("subtitulo_reporte_" + getNombreObjeto());
		}
		return subTituloReporte;
	}

	protected String getReporteLogoPath() {

		return "images/logo.png";
	}

	protected String getUsuarioInfoReporte() {

		Usuario usuario = sesionUsuario.getUsuario();
		String userStr = usuario.getNombre() + " " + usuario.getApellido()
				+ " (" + usuario.getCodigo() + ")";
		return msg.get("usuario_creador_reporte") + ": " + userStr;
	}

	protected String getFechaCreacionReporte() {

		return msg.get("fecha_creacion_reporte") + ": "
				+ util.getTimeStampFormat().format(new Date());
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String uploadFileHandler(final ModelMap model,
			@RequestParam MultipartFile file,
			@RequestParam(required = false) Long recordId) {

		if (!file.isEmpty()) {
			return uploadSuccessReturnPage(model, file, recordId);
		} else {
			return uploadEmptyFileReturnPage(model, recordId);
		}
	}

	protected String uploadSuccessReturnPage(ModelMap model,
			MultipartFile file, Long recordId) {

		try {
			byte[] bytes = file.getBytes();

			// Creating the directory to store file
			String rootPath = "/tmp";
			File dir = new File(rootPath + File.separator + "tmpFiles");
			if (!dir.exists())
				dir.mkdirs();

			// Create the file on server
			File serverFile = new File(dir.getAbsolutePath() + File.separator
					+ file.getOriginalFilename());
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
			setMensajeOk(getUploadSuccessMsg());
			return index(null, model);

		} catch (Exception e) {
			return uploadFailedReturnPage(model, recordId);
		}

	}

	protected String uploadEmptyFileReturnPage(ModelMap model, Long recordId) {

		setMensajeAtencion(msg.get("file.upload.warning"));
		return index(null, model);
	}

	protected String uploadFailedReturnPage(ModelMap model, Long recordId) {

		setMensajeError(msg.get("file.upload.error"));

		return index(null, model);
	}

	protected String getUploadSuccessMsg() {

		return msg.get("file.upload.success");
	}

	@ModelAttribute("modulo")
	public String getNombreModulo() {

		return "";
	}

}
