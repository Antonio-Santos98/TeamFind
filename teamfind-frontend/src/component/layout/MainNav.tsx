import React from 'react'
import { Link } from "react-router-dom";
import classes from "./MainNav.module.css";

const MainNav = () => {
  return (
    <header className={classes.header}>
        <Link to='/'>
            <div className={classes.logo}>TeamFinder</div>
        </Link>
        <nav>
            <ul>
                <li>
                    <Link to="/players">Players</Link>
                </li>
                <li>
                    <Link to="/teams">Teams</Link>
                </li>
                <li>
                    <Link to="/login">Login</Link>
                </li>
            </ul>
        </nav>
    </header>
  )
}

export default MainNav;
