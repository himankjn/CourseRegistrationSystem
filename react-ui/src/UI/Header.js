import React from 'react';
import wibmoLogo from '../assets/wibmoLogo.png'
import './style.css'

export default function Header(){
    return(
        <React.Fragment>
            <img className='center' src={wibmoLogo} alt="wibmoLogo"/>
        </React.Fragment>
    )
}