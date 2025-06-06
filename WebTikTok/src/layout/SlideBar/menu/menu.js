import classNames from 'classnames/bind';
import styles from './menu.module.scss';

const cx = classNames.bind(styles);
function Menu({ children }) {
    return <nav className={cx('menu')}>{children} </nav>;
}

export default Menu;
