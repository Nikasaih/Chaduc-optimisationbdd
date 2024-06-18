import requests
import json
import random
import string

# URL de base de l'API
base_url = "http://localhost:8081"

# Noms et prénoms aléatoires
first_names = ["Emma", "Liam", "Olivia", "Noah", "Ava", "William", "Isabella", "James", "Sophia", "Daniel"]
last_names = ["Smith", "Johnson", "Williams", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson"]

# Projets aléatoires
project_names = ["Projet A", "Projet B", "Projet C", "Projet D", "Projet E", "Projet F", "Projet G", "Projet H", "Projet I", "Projet J"]

# Écoles aléatoires
school_names = ["École A", "École B", "École C", "École D", "École E", "École F", "École G", "École H", "École I", "École J"]
school_domains = ["Informatique", "Génie Civil", "Électronique", "Mécanique", "Chimie", "Biologie", "Économie", "Droit", "Lettres", "Arts"]

# Adresses aléatoires
cities = ["Paris", "Lyon", "Marseille", "Lille", "Toulouse", "Nice", "Nantes", "Strasbourg", "Montpellier", "Bordeaux"]
postal_codes = [75000, 69000, 13000, 59000, 31000, 18041, 44000, 67000, 34000, 33000]

# Insertion des étudiants
for _ in range(30):
    student = {
        "nom": random.choice(last_names),
        "prenom": random.choice(first_names),
        "projets": [
            {"nomProjet": random.choice(project_names)}
        ]
    }
    response = requests.post(f"{base_url}/etudiants", json=student)
    print(f"Étudiant inséré : {response.json()}")

# Insertion des projets
for _ in range(30):
    project = {
        "nomProjet": ''.join(random.choices(string.ascii_letters + string.digits, k=10))
    }
    response = requests.post(f"{base_url}/projets", json=project)
    print(f"Projet inséré : {response.json()}")

# Insertion des écoles
for _ in range(30):
    school = {
        "nom": random.choice(school_names),
        "domaine": random.choice(school_domains),
        "etudiants": [
            {
                "nom": random.choice(last_names),
                "prenom": random.choice(first_names),
                "projets": [
                    {"nomProjet": random.choice(project_names)}
                ]
            } for _ in range(3)
        ],
        "adresse": {
            "ville": random.choice(cities),
            "complementAdresse": ''.join(random.choices(string.ascii_letters + string.digits, k=10)),
            "codePostal": random.choice(postal_codes)
        }
    }
    response = requests.post(f"{base_url}/ecoles", json=school)
    print(f"École insérée : {response.json()}")
