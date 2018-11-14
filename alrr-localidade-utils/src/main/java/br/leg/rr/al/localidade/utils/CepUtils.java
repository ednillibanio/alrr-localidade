package br.leg.rr.al.localidade.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 04-04-2012
 */
public final class CepUtils {

	private CepUtils() {

	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String unformat(String arg) {
		String cep = null;
		if (StringUtils.isNotBlank(arg)) {

			cep = StringUtils.replace(arg, "-", "");
			cep = StringUtils.trimToEmpty(cep);
			if (StringUtils.isNumeric(cep)) {
				return cep;
			}
		}
		return cep;
	}

	/**
	 * 
	 * @param arg
	 * @return
	 */
	public static String format(Integer arg) {

		if (isValid(arg.toString())) {
			return format(arg.toString());
		}
		return null;

	}

	/**
	 * 
	 * @param arg
	 * @return
	 */
	public static String format(String arg) {

		if (isValid(arg)) {
			String cep = StringUtils.leftPad(arg, 8, "0");
			cep = cep.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
			return cep;
		}
		return null;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isValid(Long obj) {
		if (obj != null) {
			return isValid(obj.toString());
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isValid(String arg) {

		String cep = unformat(arg);
		cep = StringUtils.trimToEmpty(cep);
		// verifica se cep não é nulo, e se é número.
		if (StringUtils.isNotBlank(cep) && StringUtils.isNumeric(cep)) {
			return true;
		}
		return false;

	}

}
