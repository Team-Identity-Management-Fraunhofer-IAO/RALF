package de.ralf.threatmasterkitchen.controller;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.sql.data.provider.TransformationOrderProvider;
import com.sql.hibernate.access.DataAccessObject;

import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects.FileUploadDTO;
import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects.OperatorDTO;
import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects.RequestDTO;
import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects.RequestHeaderDTO;
import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects.TransformationOrderDTO;
import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects.TransformationOrderScheduleDTO;
import de.ralf.threatmasterkitchen.data.softwarestack.datatransfer.transformationobjects.TransformationRuleDTO;
import de.securityallies.sql.data.objects.persistence.transformationengine.FileUpload;
import de.securityallies.sql.data.objects.persistence.transformationengine.Operator;
import de.securityallies.sql.data.objects.persistence.transformationengine.Request;
import de.securityallies.sql.data.objects.persistence.transformationengine.RequestHeaderProperty;
import de.securityallies.sql.data.objects.persistence.transformationengine.TransformationOperator;
import de.securityallies.sql.data.objects.persistence.transformationengine.TransformationOrder;
import de.securityallies.sql.data.objects.persistence.transformationengine.TransformationRule;
import de.securityallies.sql.data.objects.persistence.transformationengine.TransformationSchedule;

@Controller
public class DataImportController extends AbstractController {
/*
	@PostMapping(value = "/Administration/DataHarvesting/dryRun")
	@ResponseBody
	public String dryRun(@RequestBody final TransformationOrderDTO transformationOrderDTO,
			Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			if (transformationOrderDTO.getTransformation_order_id() != 0) {
				Transformator transformator = new Transformator();
				try {
					transformator.executeTransformationOrder(transformationOrderDTO.getTransformation_order_id(), true);
				} catch (XPathExpressionException | InvalidExecutionException | SAXException | IOException
						| ParserConfigurationException e) {
					e.printStackTrace();
				}
				//System.out.println(transformator.getDryRunResult());
				return "[" + transformator.getDryRunResult() + "]";
			}
		}
		return null;
	}

	@PostMapping(value = "/Administration/DataHarvesting/execute")
	public ResponseEntity execute(@RequestBody final TransformationOrderDTO transformationOrderDTO,
			Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			if (transformationOrderDTO.getTransformation_order_id() != 0) {
				Transformator transformator = new Transformator();
				try {
					transformator.executeTransformationOrder(transformationOrderDTO.getTransformation_order_id(),
							false);
				} catch (XPathExpressionException | InvalidExecutionException | SAXException | IOException
						| ParserConfigurationException e) {
					e.printStackTrace();
				}
				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/Administration/DataHarvesting/updateFileUpload", consumes = { "multipart/form-data" })
	public ResponseEntity updateFileUpload(@RequestPart("properties") final FileUploadDTO fileUploadDTO, @RequestPart("file") MultipartFile file, 
			Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			FileUpload fileUploadOperator = null;
			TransformationOrderProvider orderProvider = new TransformationOrderProvider();
			if (fileUploadDTO.getOperator_id() == 0) {
				System.out.println(fileUploadDTO.getTransformation_order_id());
				fileUploadOperator = new FileUpload();
				TransformationOperator metaOperator = new TransformationOperator();
				metaOperator.setOperator_type("FILE_UPLOAD");
				orderProvider.persistTransformationOperator(metaOperator);
				fileUploadOperator.setOperator_id(metaOperator.getOperator_id());
				TransformationOrder order = new TransformationOrder();
				order.setOperator_id(metaOperator.getOperator_id());
				order.setTransformation_order_id(fileUploadDTO.getTransformation_order_id());
				order.setTransformation_order_name(orderProvider.getNameForOrderID(fileUploadDTO.getTransformation_order_id()));
				order.setTransformation_sequence(fileUploadDTO.getOrder());
				orderProvider.persist(order);
			} else {
				fileUploadOperator = orderProvider.getFileUpload(fileUploadDTO.getOperator_id());
			}
			if (fileUploadOperator != null) {
				TransformationOrder order = orderProvider.find(fileUploadOperator.getOperator_id());
				order.setTransformation_sequence(fileUploadDTO.getOrder());
				orderProvider.persist(order);
				try {
					fileUploadOperator.setContent(file.getBytes());				
					fileUploadOperator.setFile_type(fileUploadDTO.getFile_type());
					fileUploadOperator.setFile_upload_name(fileUploadDTO.getFile_upload_name());
					fileUploadOperator.setFirst_data_line(fileUploadDTO.getFirst_data_line());
					orderProvider.persistFileUpload(fileUploadOperator);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "/Administration/DataHarvesting/deleteFileUpload")
	public ResponseEntity deleteFileUpload(@RequestBody final FileUploadDTO fileUploadDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			TransformationOrderProvider orderProvider = new TransformationOrderProvider();
			TransformationOrder order = orderProvider.find(fileUploadDTO.getOperator_id());
			TransformationOperator metaOperator = orderProvider.getTransformationOperator(fileUploadDTO.getOperator_id());
			FileUpload fileUpload = orderProvider.getFileUpload(fileUploadDTO.getOperator_id());
			orderProvider.delete(fileUpload);
			orderProvider.delete(order);
			orderProvider.delete(metaOperator);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
	
	
	@PostMapping(value = "/Administration/DataHarvesting/updateRequest")
	public ResponseEntity updateRequest(@RequestBody final RequestDTO requestDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			TransformationOrderProvider orderProvider = new TransformationOrderProvider();
			Request request = null;
			boolean updateHeaders = true;
			if (requestDTO.getOperator_id() == 0) {
				request = new Request();
				TransformationOperator metaOperator = new TransformationOperator();
				metaOperator.setOperator_type("REQUEST");
				orderProvider.persistTransformationOperator(metaOperator);
				request.setOperator_id(metaOperator.getOperator_id());
				TransformationOrder order = new TransformationOrder();
				order.setOperator_id(metaOperator.getOperator_id());
				order.setTransformation_order_id(requestDTO.getTransformation_order_id());
				order.setTransformation_order_name(
						orderProvider.getNameForOrderID(requestDTO.getTransformation_order_id()));
				order.setTransformation_sequence(requestDTO.getOrder());
				orderProvider.persist(order);

			} else {
				request = orderProvider.getRequest(requestDTO.getOperator_id());
			}
			if (request != null) {
				TransformationOrder order = orderProvider.find(request.getOperator_id());
				order.setTransformation_sequence(requestDTO.getOrder());
				orderProvider.persist(order);
				request.setBody(requestDTO.getRequest_body());
				request.setEndpoint(requestDTO.getEndpoint());
				request.setRequest_name(requestDTO.getRequest_name());
				request.setRequest_type(requestDTO.getRequest_type());
				orderProvider.persistRequest(request);

				List<RequestHeaderProperty> persistedHeaderProperties = orderProvider
						.getRequestHeaderPropertyForOperatorId(request.getOperator_id());
				List<RequestHeaderDTO> headerDTOs = requestDTO.getHeaders();
				for (RequestHeaderDTO headerDTO : headerDTOs) {
					RequestHeaderProperty headerProperty = null;
					if (headerDTO.getHeader_property_id() == 0) {
						headerProperty = new RequestHeaderProperty();
					} else {
						headerProperty = orderProvider.getRequestHeaderProperty(headerDTO.getHeader_property_id());
					}
					if (headerProperty != null) {
						headerProperty.setOperator_id(request.getOperator_id());
						headerProperty.setHeader_property_key(headerDTO.getHeader_property_key());
						headerProperty.setHeader_property_value(headerDTO.getHeader_property_value());
						orderProvider.persistRequestHeaderProperty(headerProperty);
					}
				}

				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/Administration/DataHarvesting/deleteRequest")
	public ResponseEntity deleteRequest(@RequestBody final RequestDTO requestDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			TransformationOrderProvider orderProvider = new TransformationOrderProvider();
			Request request = orderProvider.getRequest(requestDTO.getOperator_id());
			TransformationOrder order = orderProvider.find(requestDTO.getOperator_id());
			TransformationOperator metaOperator = orderProvider.getTransformationOperator(requestDTO.getOperator_id());
			orderProvider.delete(request);
			orderProvider.delete(order);
			orderProvider.delete(metaOperator);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/Administration/DataHarvesting/updateTransformationRule")
	public ResponseEntity updateTransformatioNRule(@RequestBody TransformationRuleDTO transformationRuleDTO,
			Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			TransformationOrderProvider orderProvider = new TransformationOrderProvider();
			TransformationRule transformationRule = null;
			if (transformationRuleDTO.getOperator_id() == 0) {
				transformationRule = new TransformationRule();
				TransformationOperator metaOperator = new TransformationOperator();
				metaOperator.setOperator_type("RULE");
				orderProvider.persistTransformationOperator(metaOperator);
				transformationRule.setOperator_id(metaOperator.getOperator_id());
				TransformationOrder order = new TransformationOrder();
				order.setOperator_id(metaOperator.getOperator_id());
				order.setTransformation_order_id(transformationRuleDTO.getTransformation_order_id());
				order.setTransformation_order_name(
						orderProvider.getNameForOrderID(transformationRuleDTO.getTransformation_order_id()));
				order.setTransformation_sequence(transformationRuleDTO.getOrder());
				orderProvider.persist(order);
			} else {
				transformationRule = orderProvider.getTransformationRule(transformationRuleDTO.getOperator_id());
			}
			if (transformationRule != null) {
				TransformationOrder order = orderProvider.find(transformationRule.getOperator_id());
				order.setTransformation_sequence(transformationRuleDTO.getOrder());
				orderProvider.persist(order);
				transformationRule.setRule(transformationRuleDTO.getRule());
				transformationRule.setRule_type(transformationRuleDTO.getRule_type());
				transformationRule.setTarget(transformationRuleDTO.getTarget());
				orderProvider.persistTransformationRule(transformationRule);
				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/Administration/DataHarvesting/deleteTransformationRule")
	public ResponseEntity deleteRequest(@RequestBody final TransformationRuleDTO transformationRuleDTO,
			Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			TransformationOrderProvider orderProvider = new TransformationOrderProvider();
			TransformationRule rule = orderProvider.getTransformationRule(transformationRuleDTO.getOperator_id());
			TransformationOrder order = orderProvider.find(transformationRuleDTO.getOperator_id());
			TransformationOperator metaOperator = orderProvider
					.getTransformationOperator(transformationRuleDTO.getOperator_id());
			orderProvider.delete(rule);
			orderProvider.delete(order);
			orderProvider.delete(metaOperator);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/Administration/DataHarvesting/updateOperator")
	public ResponseEntity updateOperator(@RequestBody OperatorDTO operatorDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			TransformationOrderProvider orderProvider = new TransformationOrderProvider();
			Operator operator = null;
			if (operatorDTO.getOperator_id() == 0) {
				operator = new Operator();
				TransformationOperator metaOperator = new TransformationOperator();
				metaOperator.setOperator_type("OPERATOR");
				orderProvider.persistTransformationOperator(metaOperator);
				operator.setOperator_id(metaOperator.getOperator_id());
				TransformationOrder order = new TransformationOrder();
				order.setOperator_id(metaOperator.getOperator_id());
				order.setTransformation_order_id(operatorDTO.getTransformation_order_id());
				order.setTransformation_order_name(
						orderProvider.getNameForOrderID(operatorDTO.getTransformation_order_id()));
				order.setTransformation_sequence(operatorDTO.getOrder());
				orderProvider.persist(order);
			} else {
				operator = orderProvider.getOperator(operatorDTO.getOperator_id());
			}
			if (operator != null) {
				operator.setOperator_type(operatorDTO.getOperator_type());
				orderProvider.persistOperator(operator);
				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/Administration/DataHarvesting/deleteOperator")
	public ResponseEntity deleteRequest(@RequestBody final OperatorDTO operatorDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			TransformationOrderProvider orderProvider = new TransformationOrderProvider();
			Operator operator = orderProvider.getOperator(operatorDTO.getOperator_id());
			TransformationOrder order = orderProvider.find(operatorDTO.getOperator_id());
			TransformationOperator metaOperator = orderProvider.getTransformationOperator(operatorDTO.getOperator_id());
			orderProvider.delete(operator);
			orderProvider.delete(order);
			orderProvider.delete(metaOperator);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/Administration/DataHarvesting/reorder")
	public ResponseEntity reOrder(@RequestBody final TransformationOrderDTO transformationOrderDTO,
			Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			TransformationOrderProvider orderProvider = new TransformationOrderProvider();
			orderProvider.reOrderOperator(transformationOrderDTO.getTransformation_sequence(),
					transformationOrderDTO.getOperator_id());
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/Administration/DataHarvesting/createNewOrder")
	public ResponseEntity createNewOrder(@RequestBody final TransformationOrderDTO orderDTO,
			Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			TransformationOrderProvider orderProvider = new TransformationOrderProvider();
			TransformationOperator operator = new TransformationOperator();
			operator.setOperator_type("REQUEST");
			orderProvider.persistTransformationOperator(operator);
			TransformationOrder order = new TransformationOrder();
			int maxOrderID = orderProvider.getMaxOrderID();
			order.setTransformation_order_id(maxOrderID + 1);
			System.out.println(operator.getOperator_id());
			order.setOperator_id(operator.getOperator_id());
			order.setTransformation_order_name(orderDTO.getTransformation_order_name());
			order.setTransformation_sequence(0);
			System.out.println(order.getOperator_id());
			orderProvider.persist(order);
			Request request = new Request();
			request.setOperator_id(operator.getOperator_id());
			request.setBody("");
			request.setEndpoint("");
			request.setRequest_name("");
			request.setRequest_type("GET");
			orderProvider.persistRequest(request);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value="/Administration/DataHarvesting/scheduleDataHarvester")
	public ResponseEntity createNewDataHarvesterSchedule(@RequestBody final TransformationOrderScheduleDTO transformationOrderScheduleDTO, Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			TransformationOrderProvider orderProvider = new TransformationOrderProvider();
			if (transformationOrderScheduleDTO.getTransformation_order_id() != 0) {
				TransformationSchedule sched = orderProvider.getTransformationSchedule(transformationOrderScheduleDTO.getTransformation_order_id());
				if (sched != null) {
					if (transformationOrderScheduleDTO.getHours() == 0 && transformationOrderScheduleDTO.getDays() == 0 && transformationOrderScheduleDTO.getWeeks() == 0 && transformationOrderScheduleDTO.getMonths() == 0) {
						orderProvider.deleteTransformationSchedule(sched);
					}else {
						sched.setDays(transformationOrderScheduleDTO.getDays());
						sched.setHours(transformationOrderScheduleDTO.getHours());
						sched.setWeeks(transformationOrderScheduleDTO.getWeeks());
						sched.setMonths(transformationOrderScheduleDTO.getMonths());							
						ZonedDateTime time = ZonedDateTime.now().plus(transformationOrderScheduleDTO.getHours(), ChronoUnit.HOURS).plus(transformationOrderScheduleDTO.getWeeks(),ChronoUnit.WEEKS).plus(transformationOrderScheduleDTO.getMonths()*30,ChronoUnit.DAYS).plus(transformationOrderScheduleDTO.getDays(),ChronoUnit.DAYS);
						sched.setNextExecution(time);
						orderProvider.persistTransformationSchedule(sched,true);
					}
				} else {
					sched = new TransformationSchedule();
					sched.setTransformation_order_id(transformationOrderScheduleDTO.getTransformation_order_id());
					sched.setDays(transformationOrderScheduleDTO.getDays());
					sched.setHours(transformationOrderScheduleDTO.getHours());
					sched.setWeeks(transformationOrderScheduleDTO.getWeeks());
					sched.setMonths(transformationOrderScheduleDTO.getMonths());				
					ZonedDateTime time = ZonedDateTime.now().plus(transformationOrderScheduleDTO.getHours(), ChronoUnit.HOURS).plus(transformationOrderScheduleDTO.getWeeks(),ChronoUnit.WEEKS).plus(transformationOrderScheduleDTO.getMonths()*30,ChronoUnit.DAYS).plus(transformationOrderScheduleDTO.getDays(),ChronoUnit.DAYS);
					sched.setNextExecution(time);
					orderProvider.persistTransformationSchedule(sched,false);
				}
				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
*/	
}
