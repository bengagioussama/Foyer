package tn.esprit.spring;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.Services.Bloc.BlocService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BlocServiceMockTest {

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private BlocService blocService;

    private Bloc bloc;
    private Chambre chambre;
    private Foyer foyer;

    @BeforeEach
    void beforeEach() {
        bloc = Bloc.builder()
                .idBloc(1L)
                .nomBloc("Bloc A")
                .capaciteBloc(100)
                .build();

        chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(101L)
                .build();

        foyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer A")
                .build();
    }

    @AfterEach
    void afterEach() {
        reset(blocRepository, chambreRepository, foyerRepository);
    }

//    @Order(1)
//    @RepeatedTest(4)
//    void testAddOrUpdate() {
//        // Arrange
//        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);
//
//        // Act
//        Bloc result = blocService.addOrUpdate(bloc);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(bloc, result);
//        verify(blocRepository, times(1)).save(bloc);
//    }

    @Order(2)
    @Test
    void testFindAll() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(bloc);
        when(blocRepository.findAll()).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Order(3)
    @Test
    void testFindById() {
        // Arrange
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        // Act
        Bloc result = blocService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(bloc, result);
        verify(blocRepository, times(1)).findById(1L);
    }

//    @Order(4)
//    @Test
//    void testDeleteById() {
//        // Arrange
//        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));
//        doNothing().when(chambreRepository).deleteAll(anyList());
//        doNothing().when(blocRepository).delete(any(Bloc.class));
//
//        // Act
//        blocService.deleteById(1L);
//
//        // Assert
//        verify(blocRepository, times(1)).findById(1L);
//        verify(chambreRepository, times(1)).deleteAll(bloc.getChambres());
//        verify(blocRepository, times(1)).delete(bloc);
//    }

//    @Order(5)
//    @Test
//    void testAffecterChambresABloc() {
//        // Arrange
//        List<Long> numChambres = List.of(101L, 102L);
//        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);
//        when(chambreRepository.findByNumeroChambre(101L)).thenReturn(chambre);
//        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);
//
//        // Act
//        Bloc result = blocService.affecterChambresABloc(numChambres, "Bloc A");
//
//        // Assert
//        assertNotNull(result);
//        verify(blocRepository, times(1)).findByNomBloc("Bloc A");
//        verify(chambreRepository, times(numChambres.size())).findByNumeroChambre(anyLong());
//        verify(chambreRepository, times(numChambres.size())).save(any(Chambre.class));
//    }

    @Order(6)
    @Test
    void testAffecterBlocAFoyer() {
        // Arrange
        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);
        when(foyerRepository.findByNomFoyer("Foyer A")).thenReturn(foyer);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc result = blocService.affecterBlocAFoyer("Bloc A", "Foyer A");

        // Assert
        assertNotNull(result);
        assertEquals(foyer, result.getFoyer());
        verify(blocRepository, times(1)).findByNomBloc("Bloc A");
        verify(foyerRepository, times(1)).findByNomFoyer("Foyer A");
        verify(blocRepository, times(1)).save(bloc);
    }

    @Order(7)
    @Test
    void testAjouterBlocEtSesChambres() {
        // Arrange
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(chambre);
        bloc.setChambres(chambres);

        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        // Act
        Bloc result = blocService.ajouterBlocEtSesChambres(bloc);

        // Assert
        assertNotNull(result);
        verify(chambreRepository, times(chambres.size())).save(any(Chambre.class));
    }

    @Order(8)
    @Test
    void testAjouterBlocEtAffecterAFoyer() {
        // Arrange
        when(foyerRepository.findByNomFoyer("Foyer A")).thenReturn(foyer);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc result = blocService.ajouterBlocEtAffecterAFoyer(bloc, "Foyer A");

        // Assert
        assertNotNull(result);
        assertEquals(foyer, result.getFoyer());
        verify(foyerRepository, times(1)).findByNomFoyer("Foyer A");
        verify(blocRepository, times(1)).save(bloc);
    }
}