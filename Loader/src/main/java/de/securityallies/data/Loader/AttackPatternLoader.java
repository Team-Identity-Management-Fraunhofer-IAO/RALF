package de.securityallies.data.Loader;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.NoResultException;
import org.hibernate.Transaction;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.sql.hibernate.HibernateInstance;
import com.sql.hibernate.HibernateThreatMasterLoaderInstance;

import de.securityallies.taxii2.Taxii2Client.data.stix.AttackPattern;
import de.securityallies.taxii2.Taxii2Client.data.stix.KillChainPhase;
import de.securityallies.taxii2.Taxii2Client.data.stix.MarkingDefinition;
import de.securityallies.taxii2.Taxii2Client.data.stix.Reference;

public class AttackPatternLoader {

	private HibernateThreatMasterLoaderInstance hibernateThreatLoaderInstance;

	public void loadAttackPatterns(List<AttackPattern> attackPatterns, String collection_id, Timestamp timestamp) {
		Session session = hibernateThreatLoaderInstance.getSessionFactory().openSession();
		for (AttackPattern attackPattern : attackPatterns) {
			boolean insertHub = false;
			;
			boolean insertSatellite = false;
			Transaction tx = session.beginTransaction();
			String sql = "SELECT attack_pattern_id FROM H_ATTACK_PATTERN WHERE id=:id AND collection_id=:collection_id";
			NativeQuery<Integer> query = session.createNativeQuery(sql);
			query.setParameter("id", attackPattern.getId());
			query.setParameter("collection_id", collection_id);
			Integer attack_pattern_id = null;
			try {
				attack_pattern_id = query.getSingleResult();
			} catch (NoResultException ex) {
				sql = "INSERT INTO H_ATTACK_PATTERN (id,collection_id,loadTimestamp) VALUES (:id,:collection_id,:loadTimestamp)";
				query = session.createNativeQuery(sql);
				query.setParameter("id", attackPattern.getId());
				query.setParameter("collection_id", collection_id);
				query.setParameter("loadTimestamp", timestamp);
				query.executeUpdate();
				sql = "SELECT LAST_INSERT_ID()";
				NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
				BigInteger last_attack_pattern_id = lastIDQuery.getSingleResult();
				attack_pattern_id = last_attack_pattern_id.intValueExact();
				insertHub = true;
			}
			sql = "SELECT attack_pattern_id FROM S_ATTACK_PATTERN WHERE attack_pattern_id = :attack_pattern_id AND name=:name AND created = :created AND modified = :modified AND description = :description AND x_mitre_version = :x_mitre_version AND x_mitre_is_subtechnique = :x_mitre_is_subtechnique AND x_mitre_detection = :x_mitre_detection AND revoked = :revoked";
			query = session.createNativeQuery(sql);
			query.setParameter("attack_pattern_id", attack_pattern_id);
			query.setParameter("name", attackPattern.getName());
			query.setParameter("created", attackPattern.getCreated());
			query.setParameter("modified", attackPattern.getModified());
			query.setParameter("description", attackPattern.getDescription());
			query.setParameter("x_mitre_version", attackPattern.getX_mitre_version());
			query.setParameter("x_mitre_is_subtechnique", attackPattern.isX_mitre_is_subtechnique());
			query.setParameter("x_mitre_detection", attackPattern.getX_mitre_detection());
			query.setParameter("revoked", attackPattern.isRevoked());
			try {
				attack_pattern_id = query.getSingleResult();
			} catch (NoResultException ex) {
				sql = "INSERT INTO S_ATTACK_PATTERN (attack_pattern_id,loadTimestamp,name,created,modified,description,x_mitre_version,x_mitre_is_subtechnique,x_mitre_detection,revoked) VALUES(:attack_pattern_id,:loadTimestamp,:name,:created,:modified,:description,:x_mitre_version,:x_mitre_is_subtechnique,:x_mitre_detection,:revoked)";
				query = session.createNativeQuery(sql);
				query.setParameter("attack_pattern_id", attack_pattern_id);
				query.setParameter("loadTimestamp", timestamp);
				query.setParameter("name", attackPattern.getName());
				query.setParameter("created", attackPattern.getCreated());
				query.setParameter("modified", attackPattern.getModified());
				query.setParameter("description", attackPattern.getDescription());
				query.setParameter("x_mitre_version", attackPattern.getX_mitre_version());
				query.setParameter("x_mitre_is_subtechnique", attackPattern.isX_mitre_is_subtechnique());
				query.setParameter("x_mitre_detection", attackPattern.getX_mitre_detection());
				query.setParameter("revoked", attackPattern.isRevoked());
				query.executeUpdate();
			}
			tx.commit();
			tx = session.beginTransaction();
			// identity
			sql = "SELECT identity_id FROM H_IDENTITY WHERE id=:id AND collection_id=:collection_id";
			query = session.createNativeQuery(sql);
			Integer identity_id = null;
			query.setParameter("id", attackPattern.getCreated_by_ref());
			query.setParameter("collection_id", collection_id);
			try {
				identity_id = query.getSingleResult();
			} catch (NoResultException ex) {
				System.err.println("Identity for attack pattern " + attackPattern.getId() + " does not exist!");
			}
			if (identity_id != null) {
				sql = "SELECT l_attack_pattern_identity_id FROM L_ATTACK_PATTERN_IDENTITY WHERE attack_pattern_id = :attack_pattern_id AND identity_id = :identity_id AND collection_id=:collection_id";
				query = session.createNativeQuery(sql);
				query.setParameter("collection_id", collection_id);
				query.setParameter("attack_pattern_id", attack_pattern_id);
				query.setParameter("identity_id", identity_id);
				try {
					Integer l_attack_pattern_identity_id = query.getSingleResult();
				} catch (NoResultException ex) {
					sql = "INSERT INTO L_ATTACK_PATTERN_IDENTITY (attack_pattern_id,identity_id,loadTimestamp,collection_id) VALUES(:attack_pattern_id,:identity_id,:loadTimestamp,:collection_id)";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("attack_pattern_id", attack_pattern_id);
					query.setParameter("identity_id", identity_id);
					query.setParameter("loadTimestamp", timestamp);
					query.executeUpdate();
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			// marking_definition
			if (attackPattern.getObject_marking_refs() != null) {
				for (MarkingDefinition markingDefinition : attackPattern.getObject_marking_refs()) {
					sql = "SELECT marking_definition_id FROM H_MARKING_DEFINITION WHERE id=:id AND collection_id=:collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("id", markingDefinition.getId());
					query.setParameter("collection_id", collection_id);
					Integer marking_definition_id = null;
					try {
						marking_definition_id = query.getSingleResult();
					} catch (NoResultException ex) {
						System.err.println("Marking Definition " + markingDefinition.getId() + " for Attack Pattern "
								+ attackPattern.getId() + " does not exist!");
					}
					if (marking_definition_id != null) {
						sql = "SELECT l_attack_pattern_marking_definition_id FROM L_ATTACK_PATTERN_MARKING_DEFINITION WHERE attack_pattern_id = :attack_pattern_id AND marking_definition_id=:marking_definition_id AND collection_id=:collection_id";
						query = session.createNativeQuery(sql);
						query.setParameter("attack_pattern_id", attackPattern.getId());
						query.setParameter("marking_definition_id", marking_definition_id);
						query.setParameter("collection_id", collection_id);
						try {
							Integer l_attack_pattern_marking_definition_id = query.getSingleResult();
						} catch (NoResultException ex) {
							sql = "INSERT INTO L_ATTACK_PATTERN_MARKING_DEFINITION (attack_pattern_id,marking_definition_id,loadTimestamp,collection_id) VALUES(:attack_pattern_id,:marking_definition_id,:loadTimestamp,:collection_id)";
							query = session.createNativeQuery(sql);
							query.setParameter("attack_pattern_id", attack_pattern_id);
							query.setParameter("marking_definition_id", marking_definition_id);
							query.setParameter("collection_id", collection_id);
							query.setParameter("loadTimestamp", timestamp);
							query.executeUpdate();
						}
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			// reference
			if (attackPattern.getExternal_references() != null) {
				for (Reference reference : attackPattern.getExternal_references()) {
					sql = "SELECT reference_id FROM H_REFERENCE WHERE collection_id = :collection_id AND source_name=:source_name AND external_id=:external_id AND url=:url AND description=:description";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("source_name", reference.getSource_name());
					query.setParameter("external_id", reference.getExternal_id());
					query.setParameter("url", reference.getUrl());
					query.setParameter("description", reference.getDescription());
					Integer reference_id = null;
					try {
						reference_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO H_REFERENCE (loadTimestamp,collection_id,source_name,external_id,url,description) VALUES(:loadTimestamp,:collection_id,:source_name,:external_id,:url,:description)";
						query = session.createNativeQuery(sql);
						query.setParameter("loadTimestamp", timestamp);
						query.setParameter("collection_id", collection_id);
						query.setParameter("source_name", reference.getSource_name());
						query.setParameter("external_id", reference.getExternal_id());
						query.setParameter("url", reference.getUrl());
						query.setParameter("description", reference.getDescription());
						query.executeUpdate();
						BigInteger last_reference_id = null;
						sql = "SELECT LAST_INSERT_ID()";
						NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
						last_reference_id = lastIDQuery.getSingleResult();
						reference_id = last_reference_id.intValueExact();
					}
					sql = "SELECT l_attack_pattern_reference_id FROM L_ATTACK_PATTERN_REFERENCE WHERE attack_pattern_id=:attack_pattern_id AND reference_id = :reference_id AND collection_id=:collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("attack_pattern_id", attack_pattern_id);
					query.setParameter("reference_id", reference_id);
					query.setParameter("collection_id", collection_id);
					try {
						Integer l_attack_pattern_reference_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_ATTACK_PATTERN_REFERENCE (attack_pattern_id,reference_id,loadTimestamp,collection_id) VALUES(:attack_pattern_id,:reference_id,:loadTimestamp,:collection_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("attack_pattern_id", attack_pattern_id);
						query.setParameter("reference_id", reference_id);
						query.setParameter("collection_id", collection_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			// kill_chain_phase
			if (attackPattern.getKill_chain_phases() != null) {
				for (KillChainPhase killChainPhase : attackPattern.getKill_chain_phases()) {
					sql = "SELECT kill_chain_phase_id FROM H_KILL_CHAIN_PHASE WHERE collection_id=:collection_id AND kill_chain_name=:kill_chain_name AND phase_name=:phase_name";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("kill_chain_name", killChainPhase.getKill_chain_name());
					query.setParameter("phase_name", killChainPhase.getPhase_name());
					Integer kill_chain_phase_id = null;
					try {
						kill_chain_phase_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO H_KILL_CHAIN_PHASE (collection_id,loadTimestamp,kill_chain_name,phase_name) VALUES(:collection_id,:loadTimestamp,:kill_chain_name,:phase_name)";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("kill_chain_name", killChainPhase.getKill_chain_name());
						query.setParameter("phase_name", killChainPhase.getPhase_name());
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
						sql = "SELECT LAST_INSERT_ID()";
						NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
						BigInteger last_kill_chain_phase_id = lastIDQuery.getSingleResult();
						kill_chain_phase_id = last_kill_chain_phase_id.intValueExact();
					}
					sql = "SELECT l_attack_pattern_kill_chain_phase_id FROM L_ATTACK_PATTERN_KILL_CHAIN_PHASE WHERE kill_chain_phase_id = :kill_chain_phase_id AND attack_pattern_id = :attack_pattern_id AND collection_id = :collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("kill_chain_phase_id", kill_chain_phase_id);
					query.setParameter("attack_pattern_id", attack_pattern_id);
					query.setParameter("collection_id", collection_id);
					try {
						Integer l_attack_pattern_kill_chain_phase_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_ATTACK_PATTERN_KILL_CHAIN_PHASE (kill_chain_phase_id,attack_pattern_id,loadTimestamp,collection_id) VALUES(:kill_chain_phase_id,:attack_pattern_id,:loadTimestamp,:collection_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("kill_chain_phase_id", kill_chain_phase_id);
						query.setParameter("attack_pattern_id", attack_pattern_id);
						query.setParameter("collection_id", collection_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			// x_mitre_platforms
			if (attackPattern.getX_mitre_platforms() != null) {
				for (String platform : attackPattern.getX_mitre_platforms()) {
					sql = "SELECT x_mitre_platforms_id FROM H_X_MITRE_PLATFORMS WHERE x_mitre_platform = :platform AND collection_id = :collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("platform", platform);
					query.setParameter("collection_id", collection_id);
					Integer x_mitre_platforms_id = null;
					try {
						x_mitre_platforms_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO H_X_MITRE_PLATFORMS(collection_id,loadTimestamp,x_mitre_platform) VALUES(:collection_id,:loadTimestamp,:platform)";
						query = session.createNativeQuery(sql);
						query.setParameter("platform", platform);
						query.setParameter("collection_id", collection_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
						sql = "SELECT LAST_INSERT_ID()";
						NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
						BigInteger last_x_mitre_platforms_id = lastIDQuery.getSingleResult();
						x_mitre_platforms_id = last_x_mitre_platforms_id.intValueExact();
					}
					sql = "SELECT l_attack_pattern_x_mitre_platforms_id FROM L_ATTACK_PATTERN_X_MITRE_PLATFORMS WHERE collection_id=:collection_id AND attack_pattern_id=:attack_pattern_id AND x_mitre_platforms_id=:x_mitre_platforms_id";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("attack_pattern_id", attack_pattern_id);
					query.setParameter("x_mitre_platforms_id", x_mitre_platforms_id);
					try {
						Integer l_attack_pattern_x_mitre_platforms_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_ATTACK_PATTERN_X_MITRE_PLATFORMS (loadTimestamp,collection_id,attack_pattern_id,x_mitre_platforms_id) VALUES(:loadTimestamp,:collection_id,:attack_pattern_id,:x_mitre_platforms_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("attack_pattern_id", attack_pattern_id);
						query.setParameter("x_mitre_platforms_id", x_mitre_platforms_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			// x_mitre_contributors
			if (attackPattern.getX_mitre_contributors() != null) {
				if (attackPattern.getX_mitre_contributors() != null) {
					for (String x_mitre_contributor : attackPattern.getX_mitre_contributors()) {
						sql = "SELECT x_mitre_contributors_id FROM H_X_MITRE_CONTRIBUTORS WHERE collection_id=:collection_id AND x_mitre_contributor=:x_mitre_contributor";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("x_mitre_contributor", x_mitre_contributor);
						Integer x_mitre_contributors_id = null;
						try {
							x_mitre_contributors_id = query.getSingleResult();
						} catch (NoResultException ex) {
							sql = "INSERT INTO H_X_MITRE_CONTRIBUTORS (collection_id,loadTimestamp,x_mitre_contributor) VALUES(:collection_id,:loadTimestamp,:x_mitre_contributor)";
							query = session.createNativeQuery(sql);
							query.setParameter("collection_id", collection_id);
							query.setParameter("loadTimestamp", timestamp);
							query.setParameter("x_mitre_contributor", x_mitre_contributor);
							query.executeUpdate();
							sql = "SELECT LAST_INSERT_ID()";
							NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
							BigInteger last_x_mitre_contributors_id = lastIDQuery.getSingleResult();
							x_mitre_contributors_id = last_x_mitre_contributors_id.intValueExact();
						}
						sql = "SELECT l_attack_pattern_x_mitre_contributors_id FROM L_ATTACK_PATTERN_X_MITRE_CONTRIBUTORS WHERE collection_id=:collection_id AND x_mitre_contributors_id=:x_mitre_contributors_id";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("x_mitre_contributors_id", x_mitre_contributors_id);
						try {
							Integer l_attack_pattern_x_mitre_contributors_id = query.getSingleResult();
						} catch (NoResultException ex) {
							sql = "INSERT INTO L_ATTACK_PATTERN_X_MITRE_CONTRIBUTORS (collection_id,x_mitre_contributors_id,loadTimestamp,attack_pattern_id) VALUES(:collection_id,:x_mitre_contributors_id,:loadTimestamp,:attack_pattern_id)";
							query = session.createNativeQuery(sql);
							query.setParameter("collection_id", collection_id);
							query.setParameter("x_mitre_contributors_id", x_mitre_contributors_id);
							query.setParameter("loadTimestamp", timestamp);
							query.setParameter("attack_pattern_id", attack_pattern_id);
							query.executeUpdate();
						}
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			// x_mitre_effective_permissions
			if (attackPattern.getX_mitre_effective_permissions() != null) {
				for (String x_mitre_effective_permission : attackPattern.getX_mitre_effective_permissions()) {
					sql = "SELECT x_mitre_effective_permission_id FROM H_X_MITRE_EFFECTIVE_PERMISSION WHERE collection_id = :collection_id AND x_mitre_effective_permission = :x_mitre_effective_permission";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("x_mitre_effective_permission", x_mitre_effective_permission);
					Integer x_mitre_effective_permission_id = null;
					try {
						x_mitre_effective_permission_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO H_X_MITRE_EFFECTIVE_PERMISSION (loadTimestamp,collection_id,x_mitre_effective_permission) VALUES(:loadTimestamp,:collection_id,:x_mitre_effective_permission)";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("x_mitre_effective_permission", x_mitre_effective_permission);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
						sql = "SELECT LAST_INSERT_ID()";
						NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
						BigInteger last_x_mitre_effective_permission_id = lastIDQuery.getSingleResult();
						x_mitre_effective_permission_id = last_x_mitre_effective_permission_id.intValueExact();
					}
					sql = "SELECT l_attack_pattern_x_mitre_effective_permissions_id FROM L_ATTACK_PATTERN_X_MITRE_EFFECTIVE_PERMISSIONS WHERE x_mitre_effective_permissions_id=:x_mitre_effective_permissions_id AND attack_pattern_id=:attack_pattern_id AND collection_id = :collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("x_mitre_effective_permissions_id", x_mitre_effective_permission_id);
					query.setParameter("attack_pattern_id", attack_pattern_id);
					query.setParameter("collection_id", collection_id);
					try {
						Integer l_attack_pattern_x_mitre_effective_permissions_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_ATTACK_PATTERN_X_MITRE_EFFECTIVE_PERMISSIONS (loadTimestamp,collection_id,attack_pattern_id,x_mitre_effective_permissions_id) VALUES(:loadTimestamp,:collection_id,:attack_pattern_id,:x_mitre_effective_permissions_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("x_mitre_effective_permissions_id", x_mitre_effective_permission_id);
						query.setParameter("attack_pattern_id", attack_pattern_id);
						query.setParameter("collection_id", collection_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			// x_mitre_permissions_required
			if (attackPattern.getX_mitre_permissions_required() != null) {
				for (String x_mitre_permission_required : attackPattern.getX_mitre_permissions_required()) {
					sql = "SELECT x_mitre_permissions_required_id FROM H_X_MITRE_PERMISSIONS_REQUIRED WHERE collection_id = :collection_id AND x_mitre_permissions_required=:x_mitre_permissions_required";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("x_mitre_permissions_required", x_mitre_permission_required);
					Integer x_mitre_permissions_required_id = null;
					try {
						x_mitre_permissions_required_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO H_X_MITRE_PERMISSIONS_REQUIRED (collection_id,loadTimestamp,x_mitre_permissions_required) VALUES(:collection_id,:loadTimestamp,:x_mitre_permissions_required)";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("x_mitre_permissions_required", x_mitre_permission_required);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
						sql = "SELECT LAST_INSERT_ID()";
						NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
						BigInteger last_x_mitre_permissions_required_id = lastIDQuery.getSingleResult();
						x_mitre_permissions_required_id = last_x_mitre_permissions_required_id.intValueExact();
					}
					sql = "SELECT l_attack_pattern_x_mitre_permissions_required_id FROM L_ATTACK_PATTERN_X_MITRE_PERMISSIONS_REQUIRED WHERE x_mitre_permissions_required_id=:x_mitre_permissions_required_id AND attack_pattern_id=:attack_pattern_id AND collection_id=:collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("x_mitre_permissions_required_id", x_mitre_permissions_required_id);
					query.setParameter("attack_pattern_id", attack_pattern_id);
					query.setParameter("collection_id", collection_id);
					try {
						Integer l_attack_pattern_x_mitre_permissions_required_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_ATTACK_PATTERN_X_MITRE_PERMISSIONS_REQUIRED (x_mitre_permissions_required_id,attack_pattern_id,collection_id,loadTimestamp) VALUES(:x_mitre_permissions_required_id,:attack_pattern_id,:collection_id,:loadTimestamp)";
						query = session.createNativeQuery(sql);
						query.setParameter("x_mitre_permissions_required_id", x_mitre_permissions_required_id);
						query.setParameter("attack_pattern_id", attack_pattern_id);
						query.setParameter("collection_id", collection_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			// x_mitre_defense_bypassed
			if (attackPattern.getX_mitre_defense_bypassed() != null) {
				for (String x_mitre_defense_bypassed : attackPattern.getX_mitre_defense_bypassed()) {
					sql = "SELECT x_mitre_defense_bypassed_id FROM H_X_MITRE_DEFENSE_BYPASSED WHERE collection_id=:collection_id AND x_mitre_defense_bypassed=:x_mitre_defense_bypassed";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("x_mitre_defense_bypassed", x_mitre_defense_bypassed);
					Integer x_mitre_defense_bypassed_id = null;
					try {
						x_mitre_defense_bypassed_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO H_X_MITRE_DEFENSE_BYPASSED (loadTimestamp,collection_id,x_mitre_defense_bypassed) VALUES(:loadTimestamp,:collection_id,:x_mitre_defense_bypassed)";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("x_mitre_defense_bypassed", x_mitre_defense_bypassed);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
						sql = "SELECT LAST_INSERT_ID()";
						NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
						BigInteger last_x_mitre_defense_bypassed_id = lastIDQuery.getSingleResult();
						x_mitre_defense_bypassed_id = last_x_mitre_defense_bypassed_id.intValueExact();
					}
					sql = "SELECT l_attack_pattern_x_mitre_defensive_bypassed_id FROM L_ATTACK_PATTERN_X_MITRE_DEFENSE_BYPASSED WHERE collection_id = :collection_id AND attack_pattern_id=:attack_pattern_id AND x_mitre_defense_bypassed_id=:x_mitre_defense_bypassed_id";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("attack_pattern_id", attack_pattern_id);
					query.setParameter("x_mitre_defense_bypassed_id", x_mitre_defense_bypassed_id);
					try {
						Integer l_attack_pattern_mitre_defense_bypassed_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_ATTACK_PATTERN_X_MITRE_DEFENSE_BYPASSED (collection_id,loadTimestamp,x_mitre_defense_bypassed_id,attack_pattern_id) VALUES(:collection_id,:loadTimestamp,:x_mitre_defense_bypassed_id,:attack_pattern_id)";
						query = session.createNativeQuery(sql);
						query.setParameter("collection_id", collection_id);
						query.setParameter("attack_pattern_id", attack_pattern_id);
						query.setParameter("x_mitre_defense_bypassed_id", x_mitre_defense_bypassed_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
			tx = session.beginTransaction();
			// x_mitre_data_sources
			if (attackPattern.getX_mitre_data_sources() != null) {
				for (String x_mitre_data_source : attackPattern.getX_mitre_data_sources()) {
					sql = "SELECT x_mitre_data_sources_id FROM H_X_MITRE_DATA_SOURCES WHERE collection_id=:collection_id AND x_mitre_data_source=:x_mitre_data_source";
					query = session.createNativeQuery(sql);
					query.setParameter("collection_id", collection_id);
					query.setParameter("x_mitre_data_source", x_mitre_data_source);
					Integer x_mitre_data_sources_id = null;
					try {
						x_mitre_data_sources_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO H_X_MITRE_DATA_SOURCES (loadTimestamp,collection_id,x_mitre_data_source) VALUES(:loadTimestamp,:collection_id,:x_mitre_data_source)";
						query = session.createNativeQuery(sql);
						query.setParameter("loadTimestamp", timestamp);
						query.setParameter("collection_id", collection_id);
						query.setParameter("x_mitre_data_source", x_mitre_data_source);
						query.executeUpdate();
						sql = "SELECT LAST_INSERT_ID()";
						NativeQuery<BigInteger> lastIDQuery = session.createNativeQuery(sql);
						BigInteger last_x_mitre_data_sources_id = lastIDQuery.getSingleResult();
						x_mitre_data_sources_id = last_x_mitre_data_sources_id.intValueExact();
					}
					sql = "SELECT l_attack_pattern_x_mitre_data_sources_id FROM L_ATTACK_PATTERN_X_MITRE_DATA_SOURCES WHERE x_mitre_data_sources_id=:x_mitre_data_sources_id AND attack_pattern_id=:attack_pattern_id AND collection_id=:collection_id";
					query = session.createNativeQuery(sql);
					query.setParameter("x_mitre_data_sources_id", x_mitre_data_sources_id);
					query.setParameter("attack_pattern_id", attack_pattern_id);
					query.setParameter("collection_id", collection_id);
					try {
						Integer l_attack_pattern_x_mitre_data_sources_id = query.getSingleResult();
					} catch (NoResultException ex) {
						sql = "INSERT INTO L_ATTACK_PATTERN_X_MITRE_DATA_SOURCES (x_mitre_data_sources_id,attack_pattern_id,collection_id,loadTimestamp) VALUES (:x_mitre_data_sources_id,:attack_pattern_id,:collection_id,:loadTimestamp)";
						query = session.createNativeQuery(sql);
						query.setParameter("x_mitre_data_sources_id", x_mitre_data_sources_id);
						query.setParameter("attack_pattern_id", attack_pattern_id);
						query.setParameter("collection_id", collection_id);
						query.setParameter("loadTimestamp", timestamp);
						query.executeUpdate();
					}
				}
			}
			tx.commit();
		}
	}
}
