import Menu from './menu/menu';
import MenuList from './menu/MenuList';
import styles from './slidebar.module.scss';
import routesconfig from '~/config/routes';
import classNames from 'classnames/bind';
import logo from '~/asset/img/logo.png';

const cx = classNames.bind(styles);

function SlideBar() {
    return (
        <div className={cx('SlideBar')}>
            <div className={cx('inner')}>
                <img className={cx('logo')} src={logo} alt="tiktok" />
                <input className={cx('search')} type="text" />
            </div>

            <Menu>
                <MenuList to={routesconfig.home} title={'Đề xuất'} />
            </Menu>
        </div>
    );
}

export default SlideBar;
