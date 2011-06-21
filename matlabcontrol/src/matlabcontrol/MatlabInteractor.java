package matlabcontrol;

/*
 * Copyright (c) 2011, Joshua Kaplan
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 *    disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other materials provided with the distribution.
 *  - Neither the name of matlabcontrol nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * Interacts with a session of MATLAB. The intended use of this interface is to create a wrapper around another
 * {@code MatlabInteractor} such as {@link MatlabProxy}.
 * 
 * @since 4.0.0
 * 
 * @author <a href="mailto:nonother@gmail.com">Joshua Kaplan</a>
 */
public interface MatlabInteractor<E>
{    
    /**
     * Exits MATLAB.
     * 
     * @throws MatlabInvocationException 
     */
    public void exit() throws MatlabInvocationException;

    /**
     * Evaluates a command in MATLAB. The result of this command will not be returned.
     * <br><br>
     * This is equivalent to MATLAB's {@code eval(['command'])}.
     * 
     * @param command the command to be evaluated in MATLAB
     * @throws MatlabInvocationException 
     * 
     * @see #returningEval(String, int)
     */
    public void eval(String command) throws MatlabInvocationException;

    /**
     * Evaluates a command in MATLAB. The result of this command can be returned.
     * <br><br>
     * This is equivalent to MATLAB's {@code eval(['command'])}.
     * <br><br>
     * In order for the result of this command to be returned the number of arguments to be returned must be specified
     * by {@code returnCount}. If the command you are evaluating is a MATLAB function you can determine the amount of
     * arguments it returns by using the {@code nargout} function in the MATLAB Command Window. If it returns
     * {@code -1} that means the function returns a variable number of arguments based on what you pass in. In that
     * case, you will need to manually determine the number of arguments returned. If the number of arguments returned
     * differs from {@code returnCount} then either {@code null} or an empty {@code String} will be returned.
     * 
     * @param command the command to be evaluated in MATLAB
     * @param returnCount the number of arguments that will be returned from evaluating the command
     * 
     * @see #eval(String)
     * 
     * @return result of MATLAB eval
     * @throws MatlabInvocationException 
     */
    public E returningEval(String command, int returnCount) throws MatlabInvocationException;
    
    /**
     * Calls a MATLAB function with the name {@code functionName}. Arguments to the function may be provided as
     * {@code args}, if you wish to call the function with no arguments pass in {@code null}. The result of this command
     * will not be returned.
     * <br><br>
     * The {@code Object}s in the array will be converted into MATLAB equivalents as appropriate. Importantly, this
     * means that a {@code String} will be converted to a MATLAB char array, not a variable name.
     * 
     * @param functionName name of the MATLAB function to call
     * @param args the arguments to the function, {@code null} if none
     * @throws MatlabInvocationException 
     * 
     * @see #returningFeval(String, Object[], int)
     * @see #returningFeval(String, Object[])
     */
    public void feval(String functionName, Object[] args) throws MatlabInvocationException;

    /**
     * Calls a MATLAB function with the name {@code functionName}. Arguments to the function may be provided as
     * {@code args}, if you wish to call the function with no arguments pass in {@code null}.
     * <br><br>
     * The {@code Object}s in the array will be converted into MATLAB equivalents as appropriate. Importantly, this
     * means that a {@code String} will be converted to a MATLAB char array, not a variable name.
     * <br><br>
     * The result of this function can be returned. In order for a function's return data to be returned to MATLAB it is
     * necessary to know how many arguments will be returned. This method will attempt to determine that automatically,
     * but in the case where a function has a variable number of arguments returned it will only return one of them. To
     * have all of them returned use {@link #returningFeval(String, Object[], int)} and specify the number of arguments
     * that will be returned.
     * 
     * @param functionName name of the MATLAB function to call
     * @param args the arguments to the function, {@code null} if none
     * 
     * @see #feval(String, Object[])
     * @see #returningFeval(String, Object[])
     * 
     * @return result of MATLAB function
     * @throws MatlabInvocationException 
     */
    public E returningFeval(String functionName, Object[] args) throws MatlabInvocationException;
    
    /**
     * Calls a MATLAB function with the name {@code functionName}. Arguments to the function may be provided as
     * {@code args}, if you wish to call the function with no arguments pass in {@code null}.
     * <br><br>
     * The {@code Object}s in the array will be converted into MATLAB equivalents as appropriate. Importantly, this
     * means that a {@code String} will be converted to a MATLAB char array, not a variable name.
     * <br><br>
     * The result of this function can be returned. In order for the result of this function to be returned the number
     * of arguments to be returned must be specified by {@code returnCount}. You can use the {@code nargout} function in
     * the MATLAB Command Window to determine the number of arguments that will be returned. If {@code nargout} returns
     * {@code -1} that means the function returns a variable number of arguments based on what you pass in. In that
     * case, you will need to manually determine the number of arguments returned. If the number of arguments returned
     * differs from {@code returnCount} then either only some of the items will be returned or {@code null} will be
     * returned.
     * 
     * @param functionName name of the MATLAB function to call
     * @param args the arguments to the function, {@code null} if none
     * @param returnCount the number of arguments that will be returned from this function
     * 
     * @see #feval(String, Object[])
     * @see #returningFeval(String, Object[])
     * 
     * @return result of MATLAB function
     * @throws MatlabInvocationException 
     */
    public E returningFeval(String functionName, Object[] args, int returnCount) throws MatlabInvocationException;
    
    /**
     * Sets the variable to the given {@code value}.
     * 
     * @param variableName
     * @param value
     * 
     * @throws MatlabInvocationException
     */
    public void setVariable(String variableName, Object value) throws MatlabInvocationException;
    
    /**
     * Gets the value of the variable named {@code variableName} from MATLAB.
     * 
     * @param variableName
     * 
     * @return value
     * 
     * @throws MatlabInvocationException
     */
    public E getVariable(String variableName) throws MatlabInvocationException;
            
    /**
     * Stores an object that can be accessed from MATLAB, but is not stored as a MATLAB variable. The {@code String}
     * returned from this method will retrieve the object into the MATLAB environment. This string is intended to be
     * used as if were the name of a MATLAB variable. This allows for passing objects to MATLAB and then using them
     * in an {@code eval} statement without having to expose the object as a MATLAB variable. It acts as a "read-only"
     * variable because it cannot be assigned to.
     * <br><br>
     * Simple example usage:<br>
     * <pre>
     * {@code
     * String varName = proxy.storeObject(42, false);
     * proxy.eval("newSum = currSum + " varName;
     * }
     * </pre>
     * The returned string should be considered an internal implementation detail. It should only be used in its
     * entirety, not a substring of it. The format of the string is not guaranteed to remain the same in future
     * versions.
     * 
     * @param obj object to be stored
     * @param storePermanently if {@code false} then the object will no longer be referenced or retrievable by
     * matlabcontrol after the first time it is retrieved, if {@code true} it will remain referenced and retrievable
     * during the entire time MATLAB is running
     * 
     * @return string to retrieve stored object
     * 
     * @throws MatlabInvocationException 
     */
    public String storeObject(Object obj, boolean storePermanently) throws MatlabInvocationException;
}