package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import it.deltax.produlytics.uibackend.security.CustomAccountDetailsService;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

/**
 * Test di unitò della classe CustomAccountDetailsService
 */
public class CustomAccountDetailsServiceTest {
	/**
	 * Testa il caso in cui l'utente non sia stato trovato
	 */
	@Test
	void testUsernameNotFound() {
		CustomAccountDetailsService service = new CustomAccountDetailsService(
			new CustomAccountDetailsServiceTest.AccountRepositoryNotFoundMock());

		UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
			() -> service.loadUserByUsername("user")
		);
		assert exception.getMessage().equals("usernameNotFound");
	}

	/**
	 * Testa il caso il cui l'utente sia stato trovato
	 */
	@Test
	void testOk() {
		CustomAccountDetailsService service = new CustomAccountDetailsService(
			new CustomAccountDetailsServiceTest.AccountRepositoryMock());

		service.loadUserByUsername("user");
	}

	//CLASSI MOCK
	static class AccountRepositoryNotFoundMock implements AccountRepository {
		@Override
		public <S extends AccountEntity> S save(S entity) {
			return null;
		}
		@Override
		public <S extends AccountEntity> Iterable<S> saveAll(Iterable<S> entities) {
			return null;
		}
		@Override
		public Optional<AccountEntity> findById(String s) {
			return Optional.empty();
		}
		@Override
		public boolean existsById(String s) {
			return false;
		}
		@Override
		public Iterable<AccountEntity> findAll() {
			return null;
		}
		@Override
		public Iterable<AccountEntity> findAllById(Iterable<String> strings) {
			return null;
		}
		@Override
		public long count() {
			return 0;
		}
		@Override
		public void deleteById(String s) {

		}
		@Override
		public void delete(AccountEntity entity) {

		}
		@Override
		public void deleteAllById(Iterable<? extends String> strings) {

		}
		@Override
		public void deleteAll(Iterable<? extends AccountEntity> entities) {

		}
		@Override
		public void deleteAll() {

		}
	}

	static class AccountRepositoryMock implements AccountRepository {
		@Override
		public <S extends AccountEntity> S save(S entity) {
			return null;
		}
		@Override
		public <S extends AccountEntity> Iterable<S> saveAll(Iterable<S> entities) {
			return null;
		}
		@Override
		public Optional<AccountEntity> findById(String s) {
			return Optional.of(
				new AccountEntity("user", "akdnalkelkrnfe", false, false));
		}
		@Override
		public boolean existsById(String s) {
			return false;
		}
		@Override
		public Iterable<AccountEntity> findAll() {
			return null;
		}
		@Override
		public Iterable<AccountEntity> findAllById(Iterable<String> strings) {
			return null;
		}
		@Override
		public long count() {
			return 0;
		}
		@Override
		public void deleteById(String s) {

		}
		@Override
		public void delete(AccountEntity entity) {

		}
		@Override
		public void deleteAllById(Iterable<? extends String> strings) {

		}
		@Override
		public void deleteAll(Iterable<? extends AccountEntity> entities) {

		}
		@Override
		public void deleteAll() {

		}
	}

}
