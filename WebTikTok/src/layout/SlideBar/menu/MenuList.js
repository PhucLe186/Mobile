import { NavLink } from 'react-router-dom';
import classNames from 'classnames/bind';
import styles from './menu.module.scss';
const cx = classNames.bind(styles);

function MenuList({ to, title }) {
    return (
        <NavLink className={(nav) => cx('list', { active: nav.isActive })} to={to}>
            {title}
        </NavLink>
    );
}

export default MenuList;
