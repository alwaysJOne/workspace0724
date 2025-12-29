import Header from '../Header';
import Footer from '../Footer';
import * as S from './Layout.styled';

const Layout = ({ children }) => {
  return (
    <S.LayoutContainer>
      <Header />
      <S.Main>{children}</S.Main>
      <Footer />
    </S.LayoutContainer>
  );
};

export default Layout;

