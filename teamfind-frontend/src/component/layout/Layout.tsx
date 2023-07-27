import React, {ReactNode} from 'react'
import MainNav from './MainNav'
import classes from './Layout.module.css';

interface LayoutProps{
    children: ReactNode;
}

export const Layout: React.FC<LayoutProps> = ({ children }) => {
  return (
    <div>
        <MainNav />
        <main className={classes.main}>{children}</main>
    </div>
  );
}
