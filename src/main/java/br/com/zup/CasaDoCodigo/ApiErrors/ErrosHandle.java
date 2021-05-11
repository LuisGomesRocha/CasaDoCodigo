package br.com.zup.CasaDoCodigo.ApiErrors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrosHandle {

	private MessageSource messageSource;

	public ErrosHandle(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrosResponseDto> autorValidationError(MethodArgumentNotValidException ex) {

		List<ErrosResponseDto> erros = new ArrayList<>();

		List<FieldError> errorList = ex.getBindingResult().getFieldErrors();

		errorList.forEach(e -> {
			String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			erros.add(new ErrosResponseDto(e.getField(), message));
		});

		return erros;

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CategoriaOuAutorNaoEncontrado.class)
	public ErrosResponseDto CategoriaOuAutorNaoEncontrado(CategoriaOuAutorNaoEncontrado ex) {

		ErrosResponseDto erro;
		erro = new ErrosResponseDto("Categoria ID ou Autor ID", "Id(s) não encontrado!");
		return erro;

	}

}
