import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { publicRoutes } from '~/config/index';
import Default from '~/AppComponent/DefaultLayout';
import { AuthProvider } from './AuthContext';

function App() {
    return (
        <AuthProvider>
            <div>
                <Router>
                    <div>
                        <Routes>
                            {publicRoutes.map((route, idx) => {
                                let Layout = Default;

                                const Page = route.component;
                                // if (route.layout === null) {
                                //     Layout = Fragment;
                                // } else if (route.layout===Message) {
                                //     Layout = route.layout;
                                // }
                                return (
                                    <Route
                                        key={idx}
                                        path={route.path}
                                        element={
                                            <Layout>
                                                <Page />
                                            </Layout>
                                        }
                                    />
                                );
                            })}
                        </Routes>
                    </div>
                </Router>
            </div>
        </AuthProvider>
    );
}

export default App;
