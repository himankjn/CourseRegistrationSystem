import React, { useState, useEffect } from 'react';

import Card from '../UI/Card/Card';
import classes from './Login.module.css';
import Button from '../UI/Button/Button';

const Login = (props) => {
  const [enteredEmail, setEnteredEmail] = useState('');
  const [emailIsValid, setEmailIsValid] = useState();
  const [enteredPassword, setEnteredPassword] = useState('');
  const [passwordIsValid, setPasswordIsValid] = useState();
  const [formIsValid, setFormIsValid] = useState(false);
  const [enteredRole, setEnteredRole] = useState('ADMIN');
  const emailChangeHandler = (event) => {
    setEnteredEmail(event.target.value);
  };

  const roleChangeHandler = (event) => {
    setEnteredRole(event.target.value);
  };

  const passwordChangeHandler = (event) => {
    setEnteredPassword(event.target.value);
  };

  const validateEmailHandler = () => {
    setEmailIsValid(enteredEmail.includes('@'));
  };

  const validatePasswordHandler = () => {
    setPasswordIsValid(enteredPassword.trim().length > 0);
  };

  const submitHandler = (event) => {
    event.preventDefault();
    props.onLogin(enteredEmail, enteredPassword, enteredRole);
  };

  useEffect(() => {
    const identifier = setTimeout(() => {
      setFormIsValid(
        emailIsValid
        &&
        passwordIsValid
      );
      setEmailIsValid(enteredEmail.includes('@'));
      setPasswordIsValid(enteredPassword.trim().length > 0);
    }, 500);

    return () => {
      clearTimeout(identifier);
    };
  }, [enteredEmail,emailIsValid,passwordIsValid, enteredPassword]);


  return (
    <Card className={classes.login}>
      <form onSubmit={submitHandler}>
        <div
          className={`${classes.control} ${
            emailIsValid === false ? classes.invalid : ''
          }`}
        >
          <label htmlFor="email">Username</label>
          <input
            type="email"
            id="email"
            value={enteredEmail}
            onChange={emailChangeHandler}
            onBlur={validateEmailHandler}
          />
        </div>
        <div
          className={`${classes.control} ${
            passwordIsValid === false ? classes.invalid : ''
          }`}
        >
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            value={enteredPassword}
            onChange={passwordChangeHandler}
            onBlur={validatePasswordHandler}
          />
        </div>
        <div
         className={`${classes.control}`}>
        <label htmlFor="role">Role:</label>
          <select id="role" name="role" value={enteredRole} onChange={roleChangeHandler}>
            <optgroup label="Role">
              <option value="ADMIN">Admin</option>
              <option value="PROFESSOR">Professor</option>
              <option value="STUDENT">Student</option>
            </optgroup>
          </select>
        </div>
        <div className={classes.actions}>
          <Button type="submit" className={classes.btn} disabled={!formIsValid}>
            Login
          </Button>
        </div>
      </form>
    </Card>
  );
};

export default Login;