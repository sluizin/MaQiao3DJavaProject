package com.maqiao.was.d3;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.8
 */
public class MQUtilsJDT {
	/**
	 * 判断是否是静态
	 * @param flags int
	 * @return boolean
	 */
	public static boolean isStatic(int flags) {
		return (flags & Modifier.STATIC) > 0;
	}

	/**
	 * 判断是否是静态
	 * @param iField IField
	 * @return boolean
	 */
	public static boolean isStatic(IField iField) {
		if (iField == null) return false;
		try {
			return isStatic(iField.getFlags());
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 把数组进行排序
	 * @param order boolean
	 * @param arr IField[]
	 * @return IField[]
	 */
	public static IField[] getIFieldSort(boolean order, IField... arr) {
		int len = arr.length;
		if (len <= 1) return arr;
		List<IField> list = new ArrayList<IField>(len);
		IField e;
		try {
			for (int i = 0; i < len; i++) {
				boolean s = isStatic((e = arr[i]).getFlags());
				if (order ^ s) list.add(e);
			}
			for (int i = 0; i < len; i++) {
				boolean s = isStatic((e = arr[i]).getFlags());
				if (!(order ^ s)) list.add(e);
			}
		} catch (JavaModelException f) {
			f.printStackTrace();
		}
		IField[] newArr = new IField[len];
		return list.toArray(newArr);
	}

	/**
	 * 把数组进行排序
	 * @param order boolean
	 * @param arr IMethod[]
	 * @return IMethod[]
	 */
	public static IMethod[] getIMethodSort(boolean order, IMethod... arr) {
		int len = arr.length;
		if (len <= 1) return arr;
		List<IMethod> list = new ArrayList<IMethod>(len);
		IMethod e;
		try {
			for (int i = 0; i < len; i++) {
				boolean s = isStatic((e = arr[i]).getFlags());
				if (order ^ s) list.add(e);
			}
			for (int i = 0; i < len; i++) {
				boolean s = isStatic((e = arr[i]).getFlags());
				if (!(order ^ s)) list.add(e);
			}
		} catch (JavaModelException f) {
			f.printStackTrace();
		}
		IMethod[] newArr = new IMethod[len];
		return list.toArray(newArr);
	}

	/**
	 * 注解转成名称数组
	 * @param arr IAnnotation[]
	 * @return String[]
	 */
	public static String[] getAnnotation(IAnnotation... arr) {
		int len = arr.length;
		String[] newArr = new String[len];
		if (len == 0) return newArr;
		try {
			for (int i = 0; i < len; i++)
				newArr[i] = arr[i].getSource();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return newArr;
	}

	private static ASTParser astParser = ASTParser.newParser(AST.JLS8);

	public static final CompilationUnit getCompilationUnit(char[] source) {
		astParser.setSource(source);
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		return (CompilationUnit) (astParser.createAST(null));
	}

	@Deprecated
	public static final ASTNode getUnitASTNode(char[] source) {
		astParser.setResolveBindings(true);
		astParser.setSource(source);
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		return astParser.createAST(null);
	}

	@Deprecated
	public static final ASTNode getMethodASTNode(char[] source) {
		astParser.setResolveBindings(true);
		astParser.setSource(source);
		astParser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
		return astParser.createAST(null);
	}

	@Deprecated
	public static List<Statement> getStatementList(IType iType, String methodName) {
		try {
			return getStatementList(iType.getSource().toCharArray(), methodName);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return new ArrayList<Statement>();
	}

	@Deprecated
	public static List<Statement> getStatementList(char[] source, String methodName) {
		List<Statement> list = new ArrayList<Statement>();
		if (methodName == null) return list;
		CompilationUnit comp = getCompilationUnit(source);
		GetVisitorClass getVisitorClass = new GetVisitorClass();
		getVisitorClass.methodName = methodName;
		comp.accept(getVisitorClass);
		list = getVisitorClass.list;
		return list;
	}

	@Deprecated
	public static List<MethodDeclaration> getMethodDeclarationList(IType iType) {
		try {
			return getMethodDeclarationList(iType.getSource().toCharArray());
		} catch (JavaModelException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Deprecated
	public static List<MethodDeclaration> getMethodDeclarationList(char[] source) {
		CompilationUnit comp = getCompilationUnit(source);
		GetMethodsVisitorClass getVisitorClass = new GetMethodsVisitorClass();
		comp.accept(getVisitorClass);
		return getVisitorClass.list;
	}

	/**
	 * 得到类里所有方法 MethodDeclaration
	 * @author Sunjian
	 * @version 1.0
	 * @since jdk1.8
	 */
	public static class GetMethodsVisitorClass extends ASTVisitor {
		List<MethodDeclaration> list = new ArrayList<MethodDeclaration>();

		@Override
		public boolean visit(MethodDeclaration node) {
			//IMethod method = (IMethod) methoDeclarationNode.resolveBinding().getJavaElement();
			list.add(node);
			return true;
		}

	}

	@Deprecated
	public static class GetVisitorClass extends ASTVisitor {
		List<Statement> list = null;
		String methodName = "";

		@SuppressWarnings("unchecked")
		@Override
		public boolean visit(MethodDeclaration node) {
			if (methodName == null || node == null) return false;
			if (!methodName.equals(node.getName())) return false;
			Block b = node.getBody();
			if (b == null) return false;
			list = b.statements();
			return true;
		}
	}

	/**
	 * 得到某个方法的指令集
	 * @param iType IType
	 * @param iMethod IMethod
	 * @return List<Statement>
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static final List<Statement> getStatementList(IType iType, IMethod iMethod) {
		List<MethodDeclaration> list = MQUtilsJDT.getMethodDeclarationList(iType);
		System.out.println("list.size:" + list.size());
		for (int i = 0, len = list.size(); i < len; i++) {
			MethodDeclaration e = list.get(i);
			IMethod method = (IMethod) e.resolveBinding().getJavaElement();
			if (method.equals(iMethod)) return e.getBody().statements();
		}
		return new ArrayList<Statement>();
	}

	/**
	 * 得到某个方法的指令集
	 * @param iMethod IMethod
	 * @return List<Statement>
	 */
	@SuppressWarnings("unchecked")
	public static final List<Statement> getStatementsList(IMethod iMethod) {
		if (iMethod == null) return new ArrayList<Statement>();
		MethodDeclaration m = getMethodDeclaration(iMethod);
		if (m == null) return new ArrayList<Statement>();
		return m.getBody().statements();
	}


	/**
	 * 获取MethodDeclaration
	 * @param method IMethod
	 * @return MethodDeclaration
	 */
	public static MethodDeclaration getMethodDeclaration(IMethod method) {
		MethodDeclaration e = null;
		try {
			e = findMethodDeclaration(getCompilationUnit(((IType) (method.getParent())).getSource().toCharArray()), method);
		} catch (JavaModelException e1) {
			e1.printStackTrace();
		}
		return e;
	}

	public static MethodDeclaration getMethodDeclaration(IType iType, IMethod method) {
		try {
			return findMethodDeclaration(getCompilationUnit(method.getSource().toCharArray()), method);
		} catch (JavaModelException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取MethodDeclaration
	 * @param root ASTNode
	 * @param method IMethod
	 * @return MethodDeclaration
	 */
	@Deprecated
	public static MethodDeclaration getMethodDeclaration(ASTNode root, IMethod m) {
		if (m == null) return null;
		try {
			//IMethod m = (IMethod) method.getAncestor(IJavaElement.METHOD);
			ISourceRange ms = m.getSourceRange();
			ISourceRange mj = m.getJavadocRange();
			if (mj == null || ms.getOffset() == mj.getOffset()) {
				/* 消除注释的影响 */
				return (MethodDeclaration) NodeFinder.perform(root, m.getSourceRange());
			} else {
				return (MethodDeclaration) NodeFinder.perform(root, mj.getOffset(), ms.getLength() - mj.getOffset() + ms.getOffset());
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static MethodDeclaration findMethodDeclaration(final CompilationUnit astRoot, final IMethod testMethod) {
		final MethodDeclaration[] foundMethodDeclaration = new MethodDeclaration[1];
		foundMethodDeclaration[0] = null;
		ASTVisitor astVisitor = new ASTVisitor() {
			@Override
			public boolean visit(final MethodDeclaration methodDeclaration) {
				if (methodDeclaration.getName().toString().equals(testMethod.getElementName())) {
					foundMethodDeclaration[0] = methodDeclaration;
				}
				return foundMethodDeclaration[0] != null;
			}
		};
		astRoot.accept(astVisitor);
		return foundMethodDeclaration[0];
	}
	/**
	 * 输出 Statement 指令text
	 * @param e Statement
	 * @return String
	 */
	public static final String getStatementText(Statement e) {
		if(e==null)return "";
		return e.toString();
	}
}
